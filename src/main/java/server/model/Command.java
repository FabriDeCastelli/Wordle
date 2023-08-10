package server.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import model.ServerResponse;
import model.UserRequest;

/**
 * Represents a command to be handled by the server.
 */
public interface Command {

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return true if the user request was handled successfully
     */
    boolean handle(UserRequest userRequest);

    /**
     * Writes a ServerResponse object on an output stream.
     *
     * @param out the output stream
     * @param serverResponse the server response
     * @return true if the response was sent successfully
     */
    default boolean sendResponse(ObjectOutputStream out, ServerResponse serverResponse) {
        try {
            out.writeObject(serverResponse);
            out.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
