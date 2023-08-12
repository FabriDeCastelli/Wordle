package server.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import model.ServerResponse;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a command to be handled by the server.
 */
public interface Command {

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return the server response
     */
    ServerResponse handle(@NotNull UserRequest userRequest);



}
