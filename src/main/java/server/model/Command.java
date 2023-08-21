package server.model;

import model.Response;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a command to be handled by the server.
 */
public interface Command {

    /**
     * Handles a user requestType.
     *
     * @param userRequest the user requestType
     * @return the server response
     */
    Response handle(@NotNull UserRequest userRequest);



}
