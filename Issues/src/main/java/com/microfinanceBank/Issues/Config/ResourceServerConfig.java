package com.microfinanceBank.Issues.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class ResourceServerConfig {

	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
			.authorizeExchange(authz -> authz
				.pathMatchers("/actuator/**", "/webjars/**", "/swagger-ui*/**", "/v3/api-docs/**")
				.permitAll()
				.anyExchange().authenticated()
			)
			.oauth2ResourceServer(oauth2 -> oauth2.jwt())
			.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
