package com.schoolmanagement.schoolmanagementwebsite.security;

import com.schoolmanagement.schoolmanagementwebsite.service.JwtService;
import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String authHeader = req.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }
}
