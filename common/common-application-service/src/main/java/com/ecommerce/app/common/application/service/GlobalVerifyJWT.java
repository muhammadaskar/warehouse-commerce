package com.ecommerce.app.common.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class GlobalVerifyJWT {

    public DecodedJWT verifyJWT(String token, String secret) {
        log.info("token: " + token);
        try {
            if (token == null) {
                log.warn("Token is null");
                return null;
            }
            Algorithm algorithm = Algorithm.HMAC256(secret != null ? secret : "secret-key");
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(1)
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            // Check if the token is expired
            Date expiration = jwt.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                log.warn("Token is expired");
                return null;
            }
            return jwt;
        } catch (JWTVerificationException exception) {
            log.error("Invalid Token: " + exception.getMessage());
            return null;
        }
    }
}
