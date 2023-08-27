package server.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import model.Request;
import model.enums.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.service.command.SendWordCommand;

/**
 * Tests for SendWordCommand.
 */
@DisplayName("The SendWordCommand tests")
public class SendWordCommandTests {

    @Mock
    private final UserStatisticsService userStatisticsService = mock(UserStatisticsService.class);
    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    @Mock
    private final PlayWordleService playWordleService = mock(PlayWordleService.class);
    private SendWordCommand sendWordCommand;
    private String username;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    public void setUp() {
        sendWordCommand = new SendWordCommand(
                playWordleService, userStatisticsService, authenticationService);
        username = "username";
    }

    @Test
    @DisplayName(" cannot handle a requestType that is not send word")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> sendWordCommand.handle(new Request(RequestType.LOGIN, username))
        );
    }

    @Test
    @DisplayName(" correctly throws exception when the word is null")
    void testHandleNullWord() {
        assertThrows(IllegalArgumentException.class,
            () -> sendWordCommand.handle(
                    new Request(RequestType.SENDWORD, null))
        );
    }

}
