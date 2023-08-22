package model;

import java.io.Serializable;

/**
 * Represents a word attempt.
 */
public record WordAttempt(String word, int attemptNumber) implements Serializable {
}
