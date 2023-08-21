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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * LogoutCommand Tests.
 */
@DisplayName("The LogoutCommand Tests ")
public class LogoutCommandTests {

    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private LogoutCommand logoutCommand;
    private User user;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    void setUp() {
        logoutCommand = new LogoutCommand(authenticationService);
        user = new User("username", "password");
    }


    @Test
    @DisplayName(" cannot handle a requestType that is not logout")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> logoutCommand.handle(new Request(RequestType.LOGIN, user))
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
    @DisplayName(" cannot logout a not registered user")
    void testHandleNotRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,
            () -> logoutCommand.handle(new Request(RequestType.LOGOUT, user))
        );
    }

    @Test
    @DisplayName(" can handle a correct logout requestType")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
                logoutCommand.handle(new Request(RequestType.LOGOUT, user)),
                new Response(0, "Logout successful.")
        );
    }


}
