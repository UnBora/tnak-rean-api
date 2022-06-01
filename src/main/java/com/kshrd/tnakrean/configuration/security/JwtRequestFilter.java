package com.kshrd.tnakrean.configuration.security;

import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtUtils;

    @Autowired
    UserServiceImp userServiceImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // get the value from the token
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                AppUserResponse appUserResponse = (AppUserResponse) userServiceImp.loadUserByUsername(username);


                // Use this to authenticate
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        appUserResponse, null, appUserResponse.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }


    // get only the token
    private String parseJwt(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        String prefix = "Bearer ";

        if (StringUtils.hasText(header) && header.startsWith(prefix)) {
            return header.substring(prefix.length()); // Take only the token. Verifying the process.

        }
        return null;
    }

}