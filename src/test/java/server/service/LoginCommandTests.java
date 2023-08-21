package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import model.Response;
import model.User;
import model.UserRequest;
import model.enums.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * LoginCommand tests.
 */
@DisplayName("The LoginCommand tests ")
public class LoginCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private LoginCommand loginCommand;
    private User user;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        loginCommand = new LoginCommand(authenticationService);
        user = new User("username", "password");
    }


    @Test
    @DisplayName(" cannot handle a requestType that is not login")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> loginCommand.handle(new UserRequest(RequestType.LOGOUT, user))
        );
    }

    @Test
    @DisplayName(" cannot login a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> loginCommand.handle(new UserRequest(RequestType.LOGIN, null))
        );
    }

    @Test
    @DisplayName(" cannot login a not registered user")
    void testHandleNotRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        assertEquals(
            new Response(-1, "User not registered."),
            loginCommand.handle(new UserRequest(RequestType.LOGIN, user))
        );
    }


    @Test
    @DisplayName(" cannot login a user with wrong password")
    void testHandleWrongPassword() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(new User("username", "wrong password")));
        assertEquals(
            new Response(-1, "Wrong username or password."),
            loginCommand.handle(new UserRequest(RequestType.LOGIN, user))
        );
    }

    @Test
    @DisplayName(" can handle a correct login requestType")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
            new Response(0, "Login successful."),
            loginCommand.handle(new UserRequest(RequestType.LOGIN, user))
        );
    }







}
