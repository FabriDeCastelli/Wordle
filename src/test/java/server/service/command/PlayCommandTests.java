package server.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.enums.RequestType;
import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;

/**
 * Tests for the PlayCommand class.
 */
@DisplayName("The PlayCommand tests")
public class PlayCommandTests {

    @Mock
    private final PlayWordleService playWordleService = mock(PlayWordleService.class);
    @Mock
    private final UserStatisticsService userStatisticsService = mock(UserStatisticsService.class);

    @InjectMocks
    private PlayCommand playCommand;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName(" cannot handle a requestType that is not play")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(new Request(RequestType.LOGIN))
        );
    }

    @Test
    @DisplayName(" correctly handles a request if the user has already played the game")
    void testHandleUserAlreadyPlayed() {
        when(playWordleService.hasPlayed(any(), any()))
                .thenReturn(true);
        assertEquals(
                new Response(Status.FAILURE, "You have already played the game for this word."),
                playCommand.handle(new Request(RequestType.PLAY))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayed() {
        when(playWordleService.hasPlayed(any(), any()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(any(), any()))
                .thenReturn(true);
        when(userStatisticsService.getStatisticsByUsername(any())).thenReturn(new UserStatistics());
        when(userStatisticsService.updateStatistics(any(), any())).thenReturn(true);
        assertEquals(
                new Response(Status.SUCCESS, "The user can play the game."),
                playCommand.handle(new Request(RequestType.PLAY))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayedError() {
        when(playWordleService.hasPlayed(any(), any()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(any(), any()))
                .thenReturn(false);
        assertEquals(
                new Response(Status.FAILURE, "Error while saving the played game."),
                playCommand.handle(new Request(RequestType.PLAY))
        );
    }

}
