package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tests for the Notification class.
 */
@DisplayName("The Notification tests")
public class NotificationTests {

    private final Notification notification = new Notification("message", "sender");

    @Test
    @DisplayName("can construct an object with a message and sender")
    void canConstructObjectWithMessageAndSender() {
        assertEquals("message", notification.message());
        assertEquals("sender", notification.senderUsername());
    }

    @Test
    @DisplayName("can construct an object with a message, sender and data")
    void canConstructObjectWithMessageSenderAndData() {
        final User user = new User("testUsername", "testPassword");
        final Notification notification =
                new Notification("testMessage", "testSender", user);
        assertEquals("testMessage", notification.message());
        assertEquals("testSender", notification.senderUsername());
        assertEquals(user, notification.data());
    }

}
