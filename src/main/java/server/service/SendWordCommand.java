package server.service;

import model.Request;
import model.Response;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;
import server.model.Command;


/**
 * Guess word command.
 */
public class SendWordCommand implements Command {

    private final PlayWordleService playWordleService;


    /**
     * Constructor for GuessWordCommand.
     */
    public SendWordCommand(PlayWordleService playWordleService) {
        this.playWordleService = playWordleService;
    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SENDWORD) {
            throw new IllegalArgumentException("Cannot handle a non-sendword requestType");
        } else if (request.word() == null) {
            throw new IllegalArgumentException("Cannot send a null word");
        }
        if (request.word().equals(WordExtractionService.getCurrentWord())) {
            return new Response(1, "You guessed the word!");
        }
        return new Response(0, playWordleService.guessWord(request.word()));

    }

}
