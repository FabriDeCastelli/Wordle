package model;

import java.io.Serializable;
import model.enums.RequestType;

/**
 * Represents a user requestType.
 */
public record Request(
        RequestType requestType,
        String username,
        Object data) implements Serializable {

    /**
     * Constructor for the Request.
     *
     * @param requestType the request type
     * @param username the name of the user sending the request
     */
    public Request(RequestType requestType, String username) {
        this(requestType, username, null);
    }



}
