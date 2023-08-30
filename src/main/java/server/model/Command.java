package server.model;

import model.Request;
import model.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a command to be handled by the server.
 */
public interface Command {

    /**
     * Handles a user requestType.
     *
     * @param request the request the client sent
     * @return the response provided by the server
     */
    Response handle(@NotNull Request request);

}