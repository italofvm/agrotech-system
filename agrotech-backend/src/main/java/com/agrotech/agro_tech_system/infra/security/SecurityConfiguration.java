package com.agrotech.agro_tech_system.infra.security;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Classe de configuração de segurança
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // <--- ADICIONANDO NOVA ANNOTATION PARA QUE TUDO 
//OCORRA EM MODO "SEGURO"
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter filter;
       
    // Define regras de segurança
    // dentro de uma classe anotada com @Configuration, não é necessário que 
    // os métodos @Bean sejam public
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		// *****ATIVANDO O CORS AQUI
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Mantém desativado para API
                // CSRF: Cross-Site-Request Forgery
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // a instrução acima determina que: não estamos "guardando memoria" de quem esta
                // "visitando/usando" a aplicação - portanto, cada requisição é feita como se fosse uma
                // "folha em branco"

                .authorizeHttpRequests(auth -> auth
                        // 1. Liberação do Swagger (Adicionei o /swagger-ui.html e /swagger-ui/index.html)
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 2. Liberação do H2 Console (Obrigatório para ambiente dev)
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers("/cadastro/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 3. Importante: O H2 usa frames. O Spring bloqueia por padrão. 
                // Esta linha permite que o H2 abra no navegador:
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

 // 2. ADICIONANDO O BEAN DE CONFIGURAÇÃO DE CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
       
    @Bean
    AuthenticationProvider authenticationProvider(
            AutenticacaoService autenticacaoService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(autenticacaoService);

        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
    
    // Encoder de senha
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}