package org.phoenix.paywall.security.jwt;

import java.util.Date;
import java.util.UUID;

import org.phoenix.paywall.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {
        
    @Value("${phoenix.app.jwtSecret}")
    private String jwtSecret;

    @Value("${phoenix.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        try {
            Algorithm algorithm = Algorithm.HMAC256("baeldung");
            String jwtToken = JWT.create()
                    .withIssuer("Baeldung")
                    .withSubject("Baeldung Details")
                    .withClaim("userId", userPrincipal.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 5000000L))
                    .withJWTId(UUID.randomUUID()
                            .toString())
                    .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                    .sign(algorithm);
            return jwtToken;
        } catch (JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }
    
    public String getUserNameFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("Baeldung")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim claim = decodedJWT.getClaim("userId");
        String userId = claim.asString();
        // System.out.println(userId);
        return userId;
    }

    public boolean validateJwtToken(String authToken) {
        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("Baeldung")
                .build();
        try {
            DecodedJWT decodedJWT = verifier.verify(authToken);
            
            return true;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
