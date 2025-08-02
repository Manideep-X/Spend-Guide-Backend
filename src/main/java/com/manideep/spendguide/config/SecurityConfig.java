package com.manideep.spendguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Same as .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/health", "/info", "/status", "/activate", "/login").permitAll()
                .anyRequest().authenticated()
            )
            // As it is going to be a stateless API because of the use of JWT authentication
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Can be used anywhere else by injecting it into the class
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {



    }

}
