package model;

import java.io.Serializable;

/**
 * Represents a response from the server.
 */
public record Response(int status, String message, Object data) implements Serializable {

    /**
     * Constructor for Response. Sets data to null.
     *
     * @param status the status of the response
     * @param message the message of the response
     */
    public Response(int status, String message) {
        this(status, message, null);
    }

    /**
     * Single argument constructor for Response.
     *
     * @param status the status of the response
     */
    public Response(int status) {
        this(status, "", null);
    }

    /**
     * Two argument constructor for Response.
     *
     * @param status the status of the response
     * @param data the data of the response
     */
    public Response(int status, Object data) {
        this(status, "", data);
    }

}
