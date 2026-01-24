package com.NBnY5A.cursos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class JwtService {

    @Value("${spring.jwt.privatekey}")
    private RSAPrivateKey privateKey;

    @Value("${spring.jwt.publickey}")
    private RSAPublicKey publicKey;

    @Bean
    public JwtEncoder encoder() {
        return NimbusJwtEncoder
                .withKeyPair(publicKey, privateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    public JwtDecoder decoder() {
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .signatureAlgorithm(SignatureAlgorithm.RS256).build();
    }
}
