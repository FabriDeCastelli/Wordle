package client.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AuthenticationService class.
 */
@DisplayName("The AuthenticationService tests")
public class AuthenticationServiceTests {

    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final AuthenticationService anotherAuthenticationService =
            AuthenticationService.getInstance();

    @Test
    @DisplayName(" can login a user and maintain data consistency")
    public void testLogin() {
        authenticationService.login("test");
        final Optional<String> username = authenticationService.getLoggedUser();
        final Optional<String> anotherUsername = anotherAuthenticationService.getLoggedUser();
        assertEquals(username, anotherUsername);
    }


    @Test
    @DisplayName(" can logout a user and maintain data consistency")
    public void testLogout() {
        authenticationService.logout();
        final Optional<String> username = authenticationService.getLoggedUser();
        final Optional<String> anotherUsername = anotherAuthenticationService.getLoggedUser();
        assertEquals(username, anotherUsername);
    }

}
