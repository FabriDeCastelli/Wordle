package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        User user = new User("a", "b");
        assertEquals("a", user.getUsername());
    }

    @Test
    @DisplayName(" can correctly get the password hash")
    public void testGetPasswordHash() {
        User user = new User("a", "b");
        assertEquals("b", user.getPasswordHash());
    }

    @Test
    @DisplayName(" can correctly compare two users")
    public void testEquals() {
        User user1 = new User("testUser", "testPassword");
        User user2 = new User("testUser", "testPassword");
        assertEquals(user1, user2);
    }

    @Test
    @DisplayName(" can correctly compare two users with different usernames and passwords")
    public void testEqualsDifferentUsernameAndPassword() {
        User user1 = new User("testUser", "testPassword");
        User user2 = new User("testUser2", "testPassword2");
        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName(" can correctly compare two users with different types")
    public void testEqualsDifferentType() {
        User user1 = new User("testUser2", "testPassword2");
        String user2 = "qwerty";
        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName(" can correctly compare a user with himself")
    public void testEqualsSameUser() {
        User user1 = new User("me", "myself");
        assertEquals(user1, user1);
    }

    @Test
    @DisplayName(" can correctly compute the hashcode")
    public void testHashCode() {
        User user1 = new User("wordle", "wordlePW");
        User user2 = new User("wordle", "wordlePW");
        assertEquals(user1.hashCode(), user2.hashCode());
    }


}
