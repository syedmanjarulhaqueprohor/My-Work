package com.example.spl_file;

import java.security.MessageDigest;
public class PasswordHashing {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput != null && hashedInput.equals(storedHash);
    }
}
