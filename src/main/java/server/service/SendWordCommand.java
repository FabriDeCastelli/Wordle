package server.service;

import model.ServerResponse;
import model.UserRequest;
import model.enums.Request;
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
    public ServerResponse handle(@NotNull UserRequest userRequest) {

        if (userRequest.request() != Request.SENDWORD) {
            throw new IllegalArgumentException("Cannot handle a non-sendword request");
        } else if (userRequest.word() == null) {
            throw new IllegalArgumentException("Cannot send a null word");
        }
        if (userRequest.word().equals(WordExtractionService.getCurrentWord())) {
            return new ServerResponse(1, "You guessed the word!");
        }
        return new ServerResponse(0, playWordleService.guessWord(userRequest.word()));

    }

}
