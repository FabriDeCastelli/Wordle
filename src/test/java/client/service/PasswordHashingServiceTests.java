package client.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Service to hash passwords.
 */
@DisplayName("The PasswordHashingService tests ")
public class PasswordHashingServiceTests {

    private static PasswordHashingService passwordHashingService = null;

    @BeforeAll
    public static void setUp() {
        passwordHashingService = PasswordHashingService.getInstance();
    }

    @Test
    @DisplayName(" cann correctly create an instance of the PasswordHashingService")
    public void testGetInstance() {
        assertNotNull(passwordHashingService);
    }

    @Test
    @DisplayName(" can correctly hash a password")
    public void testHashPassword() {
        final String hashedPassword = passwordHashingService.hashPassword("password");
        assertNotNull(hashedPassword);
    }

    @Test
    @DisplayName(" can correctly check if a password matches a hashed password")
    public void testPasswordMatches() {
        final String hashedPassword = passwordHashingService.hashPassword("password");
        final boolean passwordMatches =
                passwordHashingService.passwordMatches("password", hashedPassword);
        assertTrue(passwordMatches);
    }


}
