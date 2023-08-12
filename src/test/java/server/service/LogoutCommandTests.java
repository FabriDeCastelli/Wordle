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
    @DisplayName(" cannot handle a request that is not logout")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> logoutCommand.handle(new UserRequest(Request.LOGIN, user))
        );
    }

    @Test
    @DisplayName(" cannot logout a null user")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> logoutCommand.handle(new UserRequest(Request.LOGOUT, null))
        );
    }

    @Test
    @DisplayName(" cannot logout a not registered user")
    void testHandleNotRegisteredUser() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,
            () -> logoutCommand.handle(new UserRequest(Request.LOGOUT, user))
        );
    }

    @Test
    @DisplayName(" can handle a correct logout request")
    void testHandle() {
        when(authenticationService.getUserByUsername(any()))
                .thenReturn(Optional.of(user));
        assertEquals(
                logoutCommand.handle(new UserRequest(Request.LOGOUT, user)),
                new ServerResponse(0, "Logout successful.")
        );
    }


}
