package client.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.jetbrains.annotations.NotNull;

/**
 * Singleton service to handle password hashing.
 * Uses SHA-256 as encryption algorithm.
 */
public class PasswordHashingService {

    private static PasswordHashingService instance = null;

    /**
     * Private constructor.
     */
    private PasswordHashingService() {
    }

    /**
     * Returns the instance of the PasswordHashingService.
     *
     * @return the instance of the PasswordHashingService
     */
    public static PasswordHashingService getInstance() {
        if (instance == null) {
            instance = new PasswordHashingService();
        }
        return instance;
    }

    /**
     * Hashes the password.
     *
     * @param password the not hashed password
     * @return         the hashed password
     */
    public String hashPassword(@NotNull String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            final byte[] hashBytes = messageDigest.digest(passwordBytes);
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    /**
     * Checks if the password matches the hashed password.
     *
     * @param password          the not hashed password
     * @param hashedPassword    the hashed password
     * @return                  true if the password matches the hashed password
     */
    public boolean passwordMatches(@NotNull String password, String hashedPassword) {
        final String hashedInputPassword = hashPassword(password);
        return hashedInputPassword.equals(hashedPassword);
    }


}
