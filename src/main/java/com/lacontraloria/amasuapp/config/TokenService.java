package com.lacontraloria.amasuapp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String secret;

    public String generateToken(Persona persona){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            if(persona.getRoleType().equals(RoleType.USER)) {
                String token = JWT.create()
                        .withIssuer("amasuapp")
                        .withSubject(persona.getEmailPrincipal())
                        .withClaim("dniReniec", persona.getDniRieniec().toString())
                        .withClaim("roleType", persona.getRoleType().name())
                        .withExpiresAt(Instant.now().plusSeconds(1000))
                        .sign(algorithm);
                return token;
            } else if (persona.getRoleType().equals(RoleType.ADMIN)) {
                String token = JWT.create()
                        .withIssuer("amasuapp")
                        .withSubject(persona.getEmailPrincipal())
                        .withClaim("idAdministrador", persona.getIdAdministrador().toString())
                        .withClaim("roleType", persona.getRoleType().name())
                        .withExpiresAt(Instant.now().plusSeconds(1000))
                        .sign(algorithm);
                return token;
            } else if (persona.getRoleType().equals(RoleType.COORD)) {
                String token = JWT.create()
                        .withIssuer("amasuapp")
                        .withSubject(persona.getEmailPrincipal())
                        .withClaim("idCoordinador", persona.getIdCoordinador().toString())
                        .withClaim("roleType", persona.getRoleType().name())
                        .withExpiresAt(Instant.now().plusSeconds(1000))
                        .sign(algorithm);
                return token;
            } else if (persona.getRoleType().equals(RoleType.MONITOR)) {
                String token = JWT.create()
                        .withIssuer("amasuapp")
                        .withSubject(persona.getEmailPrincipal())
                        .withClaim("idMonitor", persona.getIdMonitor().toString())
                        .withClaim("roleType", persona.getRoleType().name())
                        .withExpiresAt(Instant.now().plusSeconds(1000))
                        .sign(algorithm);
                return token;

            } else {
                throw new IllegalArgumentException("RoleType not found");
            }
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("amasuapp")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

}
