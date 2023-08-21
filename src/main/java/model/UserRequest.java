package model;

import java.io.Serializable;
import model.enums.RequestType;

/**
 * Represents a user requestType.
 */
public record UserRequest(RequestType requestType, User user, String word) implements Serializable {

    /**
     * Constructor for the UserRequest.
     *
     * @param requestType the requestType type
     * @param user the user sending the requestType
     */
    public UserRequest(RequestType requestType, User user) {
        this(requestType, user, null);
    }


}
