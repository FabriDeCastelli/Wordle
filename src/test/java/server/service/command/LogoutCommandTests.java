package server.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
 * LogoutCommand Tests.
 */
@DisplayName("The LogoutCommand Tests ")
public class LogoutCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private LogoutCommand logoutCommand;
    private String username;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        logoutCommand = new LogoutCommand(authenticationService);
        username = "username";
    }


    @Test
    @DisplayName(" cannot handle a requestType that is not logout")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> logoutCommand.handle(new Request(RequestType.LOGIN, username))
        );
    }

    @Test
    @DisplayName(" cannot logout a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> logoutCommand.handle(new Request(RequestType.LOGOUT, null))
        );
    }

    @Test
    @DisplayName(" cannot logout a not logged user")
    void testHandleNotRegisteredUsername() {
        when(authenticationService.logout()).thenReturn(false);
        assertEquals(
                logoutCommand.handle(new Request(RequestType.LOGOUT, username)),
                new Response(Status.FAILURE, "User not logged in."));
    }

    @Test
    @DisplayName(" can handle a correct logout requestType")
    void testHandle() {
        when(authenticationService.logout()).thenReturn(true);
        assertEquals(
                logoutCommand.handle(new Request(RequestType.LOGOUT, username)),
                new Response(Status.SUCCESS, "Logout successful.")
        );
    }


}
