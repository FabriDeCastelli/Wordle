package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for User class.
 */
@DisplayName("The User tests ")
public class UserTests {

    @Test
    @DisplayName(" can correctly get the getUsername")
    public void testGetUsername() {
        User user = new User("testUser", "testPassword");
        assertEquals("testUser", user.getUsername());
    }

    @Test
    @DisplayName(" can correctly get the password hash")
    public void testGetPasswordHash() {
        User user = new User("testUser", "testPassword");
        assertEquals("testPassword", user.getPasswordHash());
    }

}
