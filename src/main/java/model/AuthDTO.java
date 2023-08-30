package model;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a login data transfer object.
 */
public record AuthDTO(@NotNull String username, @NotNull String password) implements Serializable {
}
