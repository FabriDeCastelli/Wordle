package service;

import java.util.Base64;

/**
 * Service to hash passwords.
 */
public class PasswordHashingService {

    private static PasswordHashingService instance = null;

    /**
     * Private constructor to prevent initialization getInstance() singleton.
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
    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    /**
     * Checks if the password matches the hashed password.
     *
     * @param password          the not hashed password
     * @param hashedPassword    the hashed password
     * @return                  true if the password matches the hashed password
     */
    public boolean passwordMatches(String password, String hashedPassword) {
        final String decodedHashedPassword = new String(Base64.getDecoder().decode(hashedPassword));
        return decodedHashedPassword.equals(password);
    }


}
