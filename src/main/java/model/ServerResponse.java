package model;

import java.io.Serializable;

/**
 * Represents a response from the server.
 */
public record ServerResponse(int status, String message, Object data) implements Serializable {

    /**
     * Constructor for ServerResponse. Sets data to null.
     *
     * @param status the status of the response
     * @param message the message of the response
     */
    public ServerResponse(int status, String message) {
        this(status, message, null);
    }

    /**
     * Single argument constructor for ServerResponse.
     *
     * @param status the status of the response
     */
    public ServerResponse(int status) {
        this(status, "", null);
    }

    /**
     * Two argument constructor for ServerResponse.
     *
     * @param status the status of the response
     * @param data the data of the response
     */
    public ServerResponse(int status, Object data) {
        this(status, "", data);
    }

}
