package model;

import java.io.Serializable;
import model.enums.Request;

/**
 * Represents a user request.
 */
public record UserRequest(Request request, User user, String word) implements Serializable {

    /**
     * Constructor for the UserRequest.
     *
     * @param request the request type
     * @param user the user sending the request
     */
    public UserRequest(Request request, User user) {
        this(request, user, null);
    }


}
