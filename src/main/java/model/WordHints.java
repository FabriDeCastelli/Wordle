package model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the hints given by the server after a user tried to guess a certain word.
 */
public record WordHints(List<Integer> correctPositions, List<Integer> presentLetters)
        implements Serializable {

}
