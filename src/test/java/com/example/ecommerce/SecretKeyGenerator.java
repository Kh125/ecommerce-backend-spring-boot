package com.example.ecommerce;

import java.security.SecureRandom;
import java.util.Base64;
import org.junit.jupiter.api.Test;
public class SecretKeyGenerator {

    @Test
    public void generateSecretKey() {
        // SecretKey key = Jwts.SIG.HS512.key().build();
        // System.out.printf("\nKey = [%s]\n", key.getEncoded());

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("JWT Secret Key: " + encodedKey);
    }
}
