package com.agrotech.agro_tech_system.infra.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final SecurityFilter filter;
	
	public SecurityConfiguration(SecurityFilter filter) {
		this.filter = filter;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				   .cors(csrf -> csrf.disable())
				   .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				   .authorizeHttpRequests(auth -> auth
					   .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
					   .requestMatchers("/h2-console/**").permitAll()
					   .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					   .requestMatchers("/cadastro/**").permitAll()
					   .anyRequest().authenticated())
				   
				   .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
				   .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				   .build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8081"));
		config.setAllowedOrigins(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedOrigins(Arrays.asList("Authorization", "Content-Type", "Accept"));
		config.setAllowCredentials(true);
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}	
	
	@Bean
	AuthenticationProvider authenticationProvider(AutenticacaoService authService, PasswordEncoder passEncoder) {
		
		var authProvider = new DaoAuthenticationProvider(authService);
		authProvider.setPasswordEncoder(passEncoder);
		
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
