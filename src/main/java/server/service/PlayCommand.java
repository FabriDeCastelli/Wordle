package server.service;

import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
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
     * Handles a user requestType.
     *
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.PLAY) {
            throw new IllegalArgumentException("Cannot handle a non-play requestType");
        } else if (request.user() == null) {
            throw new IllegalArgumentException("Cannot play a null user");
        }

        final User user = request.user();
        if (playWordleService.hasPlayed(user, WordExtractionService.getCurrentWord())) {
            return new Response(-1, "You have already played the game for this word.");
        } else if (playWordleService.addPlayedGame(user, WordExtractionService.getCurrentWord())) {
            return new Response(0, "The user can play the game.");
        }
        return new Response(-1, "Error while saving the played game.");
    }

}
