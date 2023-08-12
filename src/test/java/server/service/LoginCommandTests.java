package server.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import model.User;
import model.UserRequest;
import model.enums.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.model.Command;

/**
 * Tests for the LoginCommand.
 */
@DisplayName("The LoginCommand tests ")
public class LoginCommandTests {

    @Mock
    private static final Command command = mock(Command.class);
    private static UserRequest userRequest;
    private static LoginCommand loginCommand;
    private static ObjectOutputStream out;
    @Mock
    private static AuthenticationService authenticationService;
    private static User user;

    /**
     * Sets up the test suite.
     *
     * @throws IOException if an I/O error occurs when closing resources.
     */
    @BeforeAll
    public static void setUp() throws IOException {
        authenticationService = mock(AuthenticationService.class);
        out = new ObjectOutputStream(System.out);
        loginCommand = new LoginCommand(authenticationService);
        user = new User("testUser", "testPassword");
        userRequest = new UserRequest(Request.LOGIN, user);

    }

    @Test
    @DisplayName(" can correctly handle a login command")
    public void testHandle() {
    }

    @Test
    @DisplayName(" successfully writes the response when the user is not registered")
    public void testHandleNotRegisteredUser() {
        when(authenticationService.getUserByUsername(any())).thenReturn(Optional.empty());

    }

    @Test
    @DisplayName(" successfully writes the response when the user is registered")
    public void testHandleRegisteredUser() {
        when(authenticationService.getUserByUsername(any())).thenReturn(Optional.of(user));

    }

    @Test
    @DisplayName(" successfully writes the response "
            + "when the user is registered but the password is wrong")
    public void testHandleRegisteredUserWrongPassword() {
        final User wrongUser = new User("testUser", "wrongPassword");
        when(authenticationService.getUserByUsername(any())).thenReturn(Optional.of(wrongUser));

    }





}
