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
import model.UserStatistics;
import model.enums.RequestType;
import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.service.command.PlayCommand;

/**
 * Tests for PlayCommand.
 */
@DisplayName("The PlayCommand tests")
public class PlayCommandTests {

    @Mock
    private final PlayWordleService playWordleService = mock(PlayWordleService.class);
    @Mock
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    @Mock
    private final UserStatisticsService userStatisticsService = mock(UserStatisticsService.class);

    private PlayCommand playCommand;
    private String username;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    public void setUp() {
        playCommand =
                new PlayCommand(playWordleService, userStatisticsService, authenticationService);
        username = "username";
    }

    @Test
    @DisplayName(" cannot handle a requestType that is not play")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(new Request(RequestType.LOGIN, username))
        );
    }

    @Test
    @DisplayName(" correctly throws exception when user is null")
    void testHandleNullUser() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(new Request(RequestType.PLAY, null))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has already played the game")
    void testHandleUserAlreadyPlayed() {
        when(authenticationService.getLoggedUser())
                .thenReturn(Optional.of(new User(username, "")));
        when(playWordleService.hasPlayed(username, WordExtractionService.getCurrentWord()))
                .thenReturn(true);
        assertEquals(
                new Response(Status.FAILURE, "You have already played the game for this word."),
                playCommand.handle(new Request(RequestType.PLAY, username))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayed() {
        when(authenticationService.getLoggedUser())
                .thenReturn(Optional.of(new User(username, "")));
        when(playWordleService.hasPlayed(any(), any()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(any(), any()))
                .thenReturn(true);
        when(userStatisticsService.getStatisticsByUsername(any())).thenReturn(new UserStatistics());
        when(userStatisticsService.updateStatistics(any(), any())).thenReturn(true);
        assertEquals(
                new Response(Status.SUCCESS, "The user can play the game."),
                playCommand.handle(new Request(RequestType.PLAY, username))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayedError() {
        when(authenticationService.getLoggedUser())
                .thenReturn(Optional.of(new User(username, "")));
        when(playWordleService.hasPlayed(username, WordExtractionService.getCurrentWord()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(username, WordExtractionService.getCurrentWord()))
                .thenReturn(false);
        assertEquals(
                new Response(Status.FAILURE, "Error while saving the played game."),
                playCommand.handle(new Request(RequestType.PLAY, username))
        );
    }

}
