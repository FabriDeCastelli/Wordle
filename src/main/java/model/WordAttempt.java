package model;

import java.io.Serializable;

/**
 * Represents a trial when a user attempts to guess a word.
 *
 * @param word the word the user is attempting to guess
 * @param attemptNumber the number of the current attempt
 */
public record WordAttempt(String word, int attemptNumber) implements Serializable {
}
