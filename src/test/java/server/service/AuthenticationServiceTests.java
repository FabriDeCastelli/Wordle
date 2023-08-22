package server.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AuthenticationService.
 */
@DisplayName("The AuthenticationService tests ")
public class AuthenticationServiceTests {

    private static AuthenticationService authenticationService;

    @BeforeAll
    public static void setUp() {
        authenticationService =
                new AuthenticationService("src/test/java/server/conf/usersTest.json");
    }


    @Test
    @DisplayName(" can correctly add and delete a user to the registered users")
    public void testAddAndDeleteUser() {
        User user = new User("testUser", "testPassword");
        authenticationService.add(user);
        Optional<User> userOptional = authenticationService.getUserByUsername("testUser");
        assertTrue(userOptional.isPresent());
        assertTrue(authenticationService.delete(user));
        userOptional = authenticationService.getUserByUsername(user.getUsername());
        assertTrue(userOptional.isEmpty());
    }

    @DisplayName(" when getting a user ")
    @Nested
    class WhenGettingUser {

        private static User user;

        @BeforeAll
        public static void setUp() {
            user = new User("fabry", "fabry");
            authenticationService.add(user);
        }

        @AfterAll
        public static void tearDown() {
            authenticationService.delete(user);
        }

        @Test
        @DisplayName(" can correctly get a registered user by getUsername")
        public void testGetUserByUsername() {
            final Optional<User> user = authenticationService.getUserByUsername("fabry");
            assertTrue(user.isPresent());
        }

        @Test
        @DisplayName(" correctly return an empty optional when the user is not registered")
        public void testGetNotRegisteredUserByUsername() {
            Optional<User> user = authenticationService.getUserByUsername("notRegisteredABC");
            assertTrue(user.isEmpty());
        }

    }

    @Nested
    @DisplayName(" when adding a user ")
    class WhenAddingUser {

        private static User user;

        @BeforeAll
        public static void setUp() throws IllegalArgumentException {
            user = new User("testUser", "testPassword");
            authenticationService.add(user);
        }

        @AfterAll
        public static void tearDown() throws IllegalArgumentException {
            authenticationService.delete(user);
        }



        @Test
        @DisplayName(" should throw an IllegalArgumentException if the user is already registered")
        public void testAddAlreadyRegisteredUser() {

            assertThrows(
                    IllegalArgumentException.class,
                    () -> authenticationService.add(user));
        }


    }

    @Nested
    @DisplayName(" when deleting a user ")
    class WhenDeletingUser {

        @Test
        @DisplayName(" should throw an IllegalArgumentException if the user is not registered")
        public void testDeleteNotRegisteredUser() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> authenticationService.delete(
                            new User("notregistered", "notregistered")));
        }
    }

}