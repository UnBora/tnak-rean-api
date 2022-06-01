package com.kshrd.tnakrean.configuration.security;

import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    private String jwtSecret = "phanith**";
    private long jwtExpiration = 86400000; // 3600ms = 24000 hours

    public String generateJwtToken(Authentication authentication) {
        AppUserResponse appUserResponse = (AppUserResponse) authentication.getPrincipal();
        return Jwts
                .builder()
                .setSubject(appUserResponse.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    // Get usename by the provided token
    public String getUsernameFromJwtToken(String token) {

        if (validateJwtToken(token)) {

            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } else return null;
    }


    // create method for validation the jwtToken
    public boolean validateJwtToken(String authToken) {

        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty : {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception : {}" + e.getMessage());
        }
        return false;
    }
}