package server.service;

import model.ServerResponse;
import model.User;
import model.UserRequest;
import model.enums.Request;
import org.jetbrains.annotations.NotNull;
import server.model.Command;

/**
 * Play command.
 */
public class PlayCommand implements Command {

    private final PlayWordleService playWordleService;

    /**
     * Constructor for PlayCommand.
     *
     * @param playWordleService the play wordle service
     */
    public PlayCommand(PlayWordleService playWordleService) {
        this.playWordleService = playWordleService;
    }

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return the server response
     */
    public ServerResponse handle(@NotNull UserRequest userRequest) {

        if (userRequest.request() != Request.PLAY) {
            throw new IllegalArgumentException("Cannot handle a non-play request");
        } else if (userRequest.user() == null) {
            throw new IllegalArgumentException("Cannot play a null user");
        }

        final User user = userRequest.user();
        if (playWordleService.hasPlayed(user, WordExtractionService.currentWord)) {
            return new ServerResponse(-1, "You have already played the game for this word.");
        }
        return new ServerResponse(0, "The user can play the game.");
    }

}
