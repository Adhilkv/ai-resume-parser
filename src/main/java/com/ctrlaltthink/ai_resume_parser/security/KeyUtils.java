package com.ctrlaltthink.ai_resume_parser.security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

    private KeyUtils(){}

    public static PrivateKey loadPrivateKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath).
                replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","").
                replace("\\s","");

        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath).
                replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","").
                replace("\\s","");

        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private static String readKeyFromResource(String pemPath) throws Exception {
        try(final InputStream is = KeyUtils.class.getClassLoader().getResourceAsStream(pemPath)){
            if(is == null){
                throw new IllegalArgumentException("Key not found: " + pemPath);
            }
            return new String(is.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read key from resource: " + pemPath, e);
        }
    }

}
