package model;

import java.io.Serializable;
import model.enums.Request;

/**
 * Represents a user request.
 */
public record UserRequest(Request request, User user) implements Serializable {

}
