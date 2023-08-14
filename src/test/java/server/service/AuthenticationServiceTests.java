package server.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import client.service.PasswordHashingService;
import java.util.Optional;
import model.User;
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
        authenticationService = new AuthenticationService();
    }


    @Test
    @DisplayName(" can correctly add and delete a user to the registered users")
    public void testAddAndDeleteUser() {
        User user = new User("testUser", "testPassword");
        authenticationService.add(user);
        Optional<User> userOptional = authenticationService.getUserByUsername("testUser");
        assertTrue(userOptional.isPresent());
        assertTrue(authenticationService.delete(user));
        userOptional = authenticationService.getUserByUsername("testUser");
        assertTrue(userOptional.isEmpty());
    }

    @DisplayName(" when getting a user ")
    @Nested
    class WhenGettingUser {

        @Test
        @DisplayName(" can correctly get a registered user by getUsername")
        public void testGetUserByUsername() {
            Optional<User> user = authenticationService.getUserByUsername("fabry");
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

        @Test
        @DisplayName(" should throw an IllegalArgumentException if the user is already registered")
        public void testAddAlreadyRegisteredUser() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> authenticationService.add(
                            new User("f", PasswordHashingService.getInstance().hashPassword("f"))));
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

        @Test
        public void testExceptionWhenWritingOnFile() {
            // TODO: mock the file writer and throw an IOException when writing on the file
        }

    }



}
