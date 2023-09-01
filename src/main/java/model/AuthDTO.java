package model;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an authentication Data Transfer Object.
 *
 * @param username the username of the user that wants to be authenticated.
 * @param password the password of the user that wants to be authenticated.
 */
public record AuthDTO(@NotNull String username, @NotNull String password) implements Serializable {
}
