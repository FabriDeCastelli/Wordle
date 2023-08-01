package model;

import enums.Request;
import java.io.Serializable;

/**
 * Represents a user request.
 */
public record UserRequest(Request request, User user) implements Serializable {

}
