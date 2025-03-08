package br.com.inthurn.backend.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class IdEncryptor {

    public static String encryptId(Long id) {
        return Base64.getEncoder().encodeToString(id.toString().getBytes());
    }

    public static Long decryptId(String encryptedId){
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedId);
        String id = new String(decodedBytes, StandardCharsets.UTF_8);
        return Long.parseLong(id);
    }
}
