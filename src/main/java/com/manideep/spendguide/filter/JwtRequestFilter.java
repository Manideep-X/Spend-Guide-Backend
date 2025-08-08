package com.manideep.spendguide.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manideep.spendguide.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// OncePerRequestFilter is used to use this filter class once in every request.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // The word Bearer automatically gets added in front of the token in the request header. Which means, the one who have the token can only access the contents.
        
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // If the header is not empty and the Athorization field starts with "Bearer ", then extract the token and get the username.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtUtil.getUsername(jwtToken);
        }

        // need to check if the user is already authenticated to avoid auth override during authentication.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetching user info
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                
                // After checking the validity of the token, a auth token is created with all necessary details(username, password, authorities). 
                // Passing null in the credentical parameter, as its no longer needed after authentication for security reasons.
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                // Adding request specific metadata
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Setting the authentication info into Security context to make user appear logged-in.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // This is needed for to pass the request and response to the nest filter in the filter chain.
        filterChain.doFilter(request, response);

    }


}
