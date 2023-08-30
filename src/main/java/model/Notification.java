package model;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a notification.
 */
public record Notification(
        @NotNull String message,
        @NotNull String senderUsername,
        Object data) implements Serializable {

    /**
     * Constructor for the Notification.
     *
     * @param message          the message
     * @param senderUsername   the sender username
     */
    public Notification(String message, String senderUsername) {
        this(message, senderUsername, null);
    }

}
