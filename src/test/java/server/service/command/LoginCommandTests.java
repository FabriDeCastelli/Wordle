package server.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import model.AuthDTO;
import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.service.AuthenticationService;

/**
 * Tests for the LoginCommand class.
 */
@DisplayName("The LoginCommand tests ")
public class LoginCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private LoginCommand loginCommand;
    private User user;
    private String username;
    private String password;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        loginCommand = new LoginCommand(authenticationService);
        user = new User("username", "password");
        username = "username";
        password = "password";

    }


    @Test
    @DisplayName(" cannot handle a requestType that is not login")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> loginCommand.handle(new Request(RequestType.LOGOUT, password))
        );
    }

    @Test
    @DisplayName(" cannot login a already logged in user")
    void testHandleNotRegisteredUser() {
        when(authenticationService.getRegisteredUserByUsername(any()))
                .thenReturn(Optional.of(user));
        when(authenticationService.login(any()))
                .thenReturn(false);
        assertEquals(
            new Response(Status.FAILURE, "User already logged in."),
            loginCommand.handle(
                    new Request(RequestType.LOGIN, new AuthDTO(username, password)))
        );
    }


    @Test
    @DisplayName(" cannot login a user with wrong password")
    void testHandleWrongPassword() {
        when(authenticationService.getRegisteredUserByUsername(any()))
                .thenReturn(Optional.of(new User("username", "wrong password")));
        assertEquals(
            new Response(Status.FAILURE, "Wrong username or password."),
            loginCommand.handle(
                    new Request(RequestType.LOGIN, new AuthDTO(username, password)))

        );
    }

    @Test
    @DisplayName(" can handle a correct login requestType")
    void testHandle() {
        when(authenticationService.getRegisteredUserByUsername(any()))
                .thenReturn(Optional.of(user));
        when(authenticationService.login(any()))
                .thenReturn(true);
        assertEquals(
            new Response(Status.SUCCESS, "Login successful."),
            loginCommand.handle(
                    new Request(RequestType.LOGIN, new AuthDTO(username, password)))
        );
    }







}
