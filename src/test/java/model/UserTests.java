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
    @DisplayName(" can successfully construct a User from an AuthDTO object")
    public void testConstructor() {
        final AuthDTO authDTO = new AuthDTO("username", "password");
        final User user = new User(authDTO);
        assertEquals(authDTO.username(), user.getUsername());
        assertEquals(authDTO.password(), user.getPasswordHash());
    }

    @Test
    @DisplayName(" can correctly get the statistics")
    public void testGetStatistics() {
        final User user = new User("a", "b");
        assertEquals(0, user.getStatistics().getGamesPlayed());
        assertEquals(0, user.getStatistics().getGamesWon());
        assertEquals(0, user.getStatistics().getCurrentStreak());
        assertEquals(0, user.getStatistics().getLongestStreak());
    }

    @Test
    @DisplayName(" can correctly set the statistics")
    public void testSetStatistics() {
        final User user = new User("a", "b");
        final UserStatistics userStatistics = new UserStatistics();
        userStatistics.incrementGamesPlayed();
        userStatistics.incrementGamesWon();
        userStatistics.incrementCurrentStreak();
        user.setStatistics(userStatistics);
        assertEquals(1, user.getStatistics().getGamesPlayed());
        assertEquals(1, user.getStatistics().getGamesWon());
        assertEquals(1, user.getStatistics().getCurrentStreak());
        assertEquals(1, user.getStatistics().getLongestStreak());
    }


    @Test
    @DisplayName(" can correctly get the username")
    public void testGetUsername() {
        final User user = new User("a", "b");
        assertEquals("a", user.getUsername());
    }

    @Test
    @DisplayName(" can correctly get the password hash")
    public void testGetPasswordHash() {
        final User user = new User("a", "b");
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
        final User user1 = new User("testUser", "testPassword");
        final User user2 = new User("testUser2", "testPassword2");
        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName(" can correctly compare two users with different types")
    public void testEqualsDifferentType() {
        final User user1 = new User("testUser2", "testPassword2");
        final String randomString = "qwerty";
        assertNotEquals(user1, randomString);
    }

    @Test
    @DisplayName(" can correctly compare a user with himself")
    public void testEqualsSameUser() {
        final User user1 = new User("me", "myself");
        assertEquals(user1, new User("me", "myself"));
    }

    @Test
    @DisplayName(" can correctly compute the hashcode")
    public void testHashCode() {
        final User user1 = new User("wordle", "wordlePW");
        final User user2 = new User("wordle", "wordlePW");
        assertEquals(user1.hashCode(), user2.hashCode());
    }


}
