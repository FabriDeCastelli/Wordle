package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AuthDTO class.
 */
@DisplayName("The AuthDTO tests ")
public class AuthDTOTests {

    @Test
    @DisplayName(" can successfully construct an AuthDTO")
    public void testConstructor() {
        String username = "username";
        String password = "password";
        final AuthDTO authDTO = new AuthDTO(username, password);
        assertEquals(username, authDTO.username());
        assertEquals(password, authDTO.password());
    }

}
