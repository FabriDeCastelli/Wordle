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
import model.enums.RequestType;
import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.service.AuthenticationService;

/**
 * Tests for the RegisterCommand class.
 */
@DisplayName("The RegisterCommand Tests ")
public class RegisterCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private RegisterCommand registerCommand;
    private String username;
    private String password;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        registerCommand = new RegisterCommand(authenticationService);
        username = "username";
        password = "password";
    }


    @Test
    @DisplayName(" cannot handle a requestType that is not register")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new Request(RequestType.LOGOUT, password))
        );
    }

    @Test
    @DisplayName(" cannot register a null AuthDTO")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> registerCommand.handle(new Request(RequestType.REGISTER, null))
        );
    }


    @Test
    @DisplayName(" cannot register a user with an empty password")
    void testHandleEmptyPassword() {
        assertEquals(
                new Response(Status.FAILURE, "Username or password cannot be empty."),
                registerCommand.handle(
                        new Request(RequestType.REGISTER, new AuthDTO(username, "")))
        );
    }

    @Test
    @DisplayName(" cannot register a user that is already registered")
    void testHandleAlreadyRegisteredUser() {
        when(authenticationService.register(any()))
                .thenReturn(false);
        assertEquals(
            new Response(Status.FAILURE, "User already registered."),
            registerCommand.handle(
                    new Request(RequestType.REGISTER, new AuthDTO(username, password)))
        );
    }

    @Test
    @DisplayName(" can handle a correct register requestType")
    void testHandle() {
        when(authenticationService.getRegisteredUserByUsername(any()))
                .thenReturn(Optional.empty());
        when(authenticationService.register(any()))
                .thenReturn(true);
        assertEquals(
            registerCommand.handle(
                    new Request(RequestType.REGISTER, new AuthDTO(username, password))),
            new Response(Status.SUCCESS, "Registration successful.")
        );
    }




}
