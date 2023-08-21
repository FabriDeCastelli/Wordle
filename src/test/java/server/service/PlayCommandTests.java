package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Tests for PlayCommand.
 */
@DisplayName("The PlayCommand tests")
public class PlayCommandTests {

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
    @DisplayName(" cannot handle a requestType that is not play")
    void testHandleInvalidRequest() {
        assertThrows(IllegalArgumentException.class,
            () -> playCommand.handle(new Request(RequestType.LOGIN, user))
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
        when(playWordleService.hasPlayed(user, WordExtractionService.getCurrentWord()))
                .thenReturn(true);
        assertEquals(
                new Response(-1, "You have already played the game for this word."),
                playCommand.handle(new Request(RequestType.PLAY, user))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayed() {
        when(playWordleService.hasPlayed(user, WordExtractionService.getCurrentWord()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(user, WordExtractionService.getCurrentWord()))
                .thenReturn(true);
        assertEquals(
                new Response(0, "The user can play the game."),
                playCommand.handle(new Request(RequestType.PLAY, user))
        );
    }

    @Test
    @DisplayName(" correctly handles a requestType if the user has not played the game")
    void testHandleUserNotPlayedError() {
        when(playWordleService.hasPlayed(user, WordExtractionService.getCurrentWord()))
                .thenReturn(false);
        when(playWordleService.addPlayedGame(user, WordExtractionService.getCurrentWord()))
                .thenReturn(false);
        assertEquals(
                new Response(-1, "Error while saving the played game."),
                playCommand.handle(new Request(RequestType.PLAY, user))
        );
    }

}
