package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import model.enums.Request;
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
    @DisplayName(" cannot handle a request that is not login")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> loginCommand.handle(new UserRequest(Request.LOGOUT, user))
        );
    }

    @Test
    @DisplayName(" cannot login a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> loginCommand.handle(new UserRequest(Request.LOGIN, null))
        );
    }

    @Test
    @DisplayName(" cannot login a not registered user")
    void testHandleNotRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        assertEquals(
            new ServerResponse(-1, "User not registered."),
            loginCommand.handle(new UserRequest(Request.LOGIN, user))
        );
    }


    @Test
    @DisplayName(" cannot login a user with wrong password")
    void testHandleWrongPassword() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(new User("username", "wrong password")));
        assertEquals(
            new ServerResponse(-1, "Wrong username or password."),
            loginCommand.handle(new UserRequest(Request.LOGIN, user))
        );
    }

    @Test
    @DisplayName(" can handle a correct login request")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
            new ServerResponse(0, "Login successful."),
            loginCommand.handle(new UserRequest(Request.LOGIN, user))
        );
    }







}
