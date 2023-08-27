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
        authenticationService.register(user);
        Optional<User> userOptional = authenticationService.getRegisteredUserByUsername("testUser");
        assertTrue(userOptional.isPresent());
        assertTrue(authenticationService.unregister(user));
        userOptional = authenticationService.getRegisteredUserByUsername(user.getUsername());
        assertTrue(userOptional.isEmpty());
    }

    @DisplayName(" when getting a user ")
    @Nested
    class WhenGettingUser {

        private static User user;

        @BeforeAll
        public static void setUp() {
            user = new User("fabry", "fabry");
            authenticationService.register(user);
        }

        @AfterAll
        public static void tearDown() {
            authenticationService.unregister(user);
        }

        @Test
        @DisplayName(" can correctly get a registered user by getUsername")
        public void testGetUserByUsername() {
            final Optional<User> user = authenticationService.getRegisteredUserByUsername("fabry");
            assertTrue(user.isPresent());
        }

        @Test
        @DisplayName(" correctly return an empty optional when the user is not registered")
        public void testGetNotRegisteredUserByUsername() {
            final Optional<User> user =
                    authenticationService.getRegisteredUserByUsername("notRegisteredABC");
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
            authenticationService.register(user);
        }

        @AfterAll
        public static void tearDown() throws IllegalArgumentException {
            authenticationService.unregister(user);
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
                    () -> authenticationService.unregister(
                            new User("notregistered", "notregistered")));
        }
    }

}