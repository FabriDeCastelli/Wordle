package model;

import java.io.Serializable;
import model.enums.Status;

/**
 * Represents a response from the server.
 */
public record Response(Status status, String message, Object data) implements Serializable {

    /**
     * Constructor for Response. Sets data to null.
     *
     * @param status the status of the response
     * @param message the message of the response
     */
    public Response(Status status, String message) {
        this(status, message, null);
    }

    /**
     * Two argument constructor for Response.
     *
     * @param status the status of the response
     * @param data the data of the response
     */
    public Response(Status status, Object data) {
        this(status, "", data);
    }

}
