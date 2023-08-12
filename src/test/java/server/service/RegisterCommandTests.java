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
 * RegisterCommand Tests.
 */
@DisplayName("The RegisterCommand Tests ")
public class RegisterCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private RegisterCommand registerCommand;
    private User user;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        registerCommand = new RegisterCommand(authenticationService);
        user = new User("username", "password");
    }

    @Test
    @DisplayName(" cannot handle a request that is not register")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new UserRequest(Request.LOGOUT, user))
        );
    }

    @Test
    @DisplayName(" cannot register a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new UserRequest(Request.REGISTER, null))
        );
    }


    @Test
    @DisplayName(" cannot register a user with an empty password")
    void testHandleEmptyPassword() {
        assertEquals(
                new ServerResponse(-1, "Password cannot be empty."),
                registerCommand.handle(
                        new UserRequest(Request.REGISTER,
                                new User("username", "")))
        );
    }

    @Test
    @DisplayName(" cannot register a user that is already registered")
    void testHandleAlreadyRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
            new ServerResponse(-1, "User already registered."),
            registerCommand.handle(new UserRequest(Request.REGISTER, user))
        );
    }

    @Test
    @DisplayName(" can handle a correct register request")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        when(authenticationService.add(any()))
                .thenReturn(true);
        assertEquals(
            registerCommand.handle(new UserRequest(Request.REGISTER, user)),
            new ServerResponse(0, "Registration successful.")
        );
    }

    @Test
    @DisplayName(" can handle a correct reqister request that fails to add the user")
    void testHandleFailedAdd() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        when(authenticationService.add(any()))
                .thenReturn(false);
        assertEquals(
            registerCommand.handle(new UserRequest(Request.REGISTER, user)),
            new ServerResponse(-1, "Registration failed.")
        );
    }



}
