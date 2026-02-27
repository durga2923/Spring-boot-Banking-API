package com.microfinanceBank.Transaction.Config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Slf4j
public class ResourceServerConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.formLogin(login -> login.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/actuator/**", "/webjars/**", "/swagger-ui*/**", "/v3/api-docs/**")
				.permitAll()
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
		return http.build();
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
		return jwtConverter;
	}

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null) {
				var details = (Jwt) authentication.getPrincipal();
				requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", details.getTokenValue()));
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties) {
		return new LoadBalancerClientFactory(properties) {

			@Override
			public GenericApplicationContext createContext(String name) {
				ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
				Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
				GenericApplicationContext context = super.createContext(name);
				Thread.currentThread().setContextClassLoader(originalClassLoader);
				return context;
			}
		};
	}

}