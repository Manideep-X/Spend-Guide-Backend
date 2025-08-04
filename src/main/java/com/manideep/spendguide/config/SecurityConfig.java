package com.manideep.spendguide.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Same as .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/health", "/info", "/status", "/activate", "/login").permitAll()
                        .anyRequest().authenticated())
                // As it is going to be a stateless API because of the use of JWT authentication
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Can be used anywhere else by injecting it into that class
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration cors = new CorsConfiguration();

        // Allowed Domains
        cors.setAllowedOriginPatterns(List.of("${FRONTEND_URL}"));

        // Allowed HTTP methods
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed HTTP headers
        cors.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        
        // Allows cookies/auth token in the CORS
        cors.setAllowCredentials(true);
        
        // This is used to map the CORS rule to a specific URL path in the backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Register the CORS config to all backend endpoints("/**")
        source.registerCorsConfiguration("/**", cors);
        
        return source;

    }

}
