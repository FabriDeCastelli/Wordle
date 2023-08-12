package model;

import java.io.Serializable;

/**
 * Represents a response from the server.
 */
public record ServerResponse(int status, String message) implements Serializable {
}
