package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.service.command.RegisterCommand;

/**
 * RegisterCommand Tests.
 */
@DisplayName("The RegisterCommand Tests ")
public class RegisterCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private RegisterCommand registerCommand;
    private User user;
    private String username;
    private String password;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        registerCommand = new RegisterCommand(authenticationService);
        user = new User("username", "password");
        username = "username";
        password = "password";
    }


    @Test
    @DisplayName(" cannot handle a requestType that is not register")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new Request(RequestType.LOGOUT, username, password))
        );
    }

    @Test
    @DisplayName(" cannot register a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new Request(RequestType.REGISTER, null, null))
        );
    }


    @Test
    @DisplayName(" cannot register a user with an empty password")
    void testHandleEmptyPassword() {
        assertEquals(
                new Response(Status.FAILURE, "Password cannot be empty."),
                registerCommand.handle(
                        new Request(RequestType.REGISTER, username, ""))
        );
    }

    @Test
    @DisplayName(" cannot register a user that is already registered")
    void testHandleAlreadyRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
            new Response(Status.FAILURE, "User already registered."),
            registerCommand.handle(new Request(RequestType.REGISTER, username, password))
        );
    }

    @Test
    @DisplayName(" can handle a correct register requestType")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        when(authenticationService.add(any()))
                .thenReturn(true);
        assertEquals(
            registerCommand.handle(new Request(RequestType.REGISTER, username, password)),
            new Response(Status.SUCCESS, "Registration successful.")
        );
    }

    @Test
    @DisplayName(" can handle a correct reqister requestType that fails to add the user")
    void testHandleFailedAdd() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        when(authenticationService.add(any()))
                .thenReturn(false);
        assertEquals(
            registerCommand.handle(new Request(RequestType.REGISTER, username, password)),
            new Response(Status.FAILURE, "Registration failed.")
        );
    }



}
