package server.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import model.User;
import model.UserRequest;
import model.enums.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Tests for SendWordCommand.
 */
@DisplayName("The SendWordCommand tests")
public class SendWordCommandTests {

    @Mock
    private final PlayWordleService playWordleService = mock(PlayWordleService.class);
    private PlayCommand playCommand;
    private User user;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    public void setUp() {
        playCommand = new PlayCommand(playWordleService);
        user = new User("username", "password");
    }

    @Test
    @DisplayName(" cannot handle a request that is not send word")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(new UserRequest(Request.LOGIN, user))
        );
    }

    @Test
    @DisplayName(" correctly throws exception when the word is null")
    void testHandleNullWord() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(
                    new UserRequest(Request.SENDWORD, new User("", ""), null))
        );
    }

}
