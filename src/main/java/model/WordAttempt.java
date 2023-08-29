package model;

import java.io.Serializable;

/**
 * Represents a trial when a user attempts to guess a word.
 */
public record WordAttempt(String word, int attemptNumber) implements Serializable {
}
