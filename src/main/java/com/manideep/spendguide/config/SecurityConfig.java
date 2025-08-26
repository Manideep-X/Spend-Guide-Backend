package com.manideep.spendguide.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.manideep.spendguide.filter.JwtRequestFilter;
import com.manideep.spendguide.service.TheUserDetailsService;

@Configuration
public class SecurityConfig {

    private final TheUserDetailsService theUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public SecurityConfig(TheUserDetailsService theUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.theUserDetailsService = theUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Same as .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(configurer -> configurer
                    .requestMatchers("/health", "/info", "/status", "/activate", "/login", "/register").permitAll()
                    .anyRequest().authenticated())

            // Instead of DaoAuthenticationProvider(deprecated), UserDetailsService can be pluged in security filter clain.
            // Spring will automatically look for PasswordEncoder & autowire it when encountered a UserDeatilsService.
            .userDetailsService(theUserDetailsService)
            
            // As it is going to be a stateless API because of the use of JWT authentication
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Can be used anywhere else by injecting it into that class
    }

    // CORS config method
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration cors = new CorsConfiguration();

        // Allowed Domains
        cors.setAllowedOriginPatterns(List.of(frontendUrl));

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

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        // Tells Spring to use authentication logic that is mentioned in the security filter chain.
        return authConfig.getAuthenticationManager();
        
    }


}


/*
//-------------------------------------------------
//                DEPRECATED CODE
//-------------------------------------------------
// DaoAuthenticationProvider, 
   AuthenticationManagerBuilder are now deprecated.
   It can be done in security filter chain now
//-------------------------------------------------

// Authentication Manager method
@Bean
AuthenticationManager authenticationManager() {

    // It's a build-in class to help authenticate user credentials using a UserDetailsService and a PasswordEncoder
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    
    // It helps spring to load user using email/username
    authenticationProvider.setUserDetailsService(theUserDetailsService);

    // Set the password encoder to hash/verify user password
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    // It helps to authenticate using the list of DaoAuthenticationProvider
    return new ProviderManager(authenticationProvider);
}
*/