package com.microfinanceBank.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.formLogin(login -> login.disable())
//				.headers(headerSpec -> headerSpec.contentSecurityPolicy("script-src 'self'"))
				.authorizeExchange(authz -> authz
						.pathMatchers("/actuator/**","/customer/actuator/**","/issue/actuator/**","/transaction/actuator/**",
								"/employee/actuator/**","/loan/actuator/**")
						.permitAll()
						.pathMatchers("/webjars/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**",
								"/swagger-ui.html","/customer/v3/api-docs/**","/transaction/v3/api-docs/**",
								"/employee/v3/api-docs/**","/issue/v3/api-docs/**","/loan/v3/api-docs/**")
						.permitAll()
						.anyExchange()
						.authenticated()
				)
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt());

		return http.build();

	}

}
