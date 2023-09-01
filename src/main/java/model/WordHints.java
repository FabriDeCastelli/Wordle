package model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the hints given by the server after a user tried to guess a certain word.
 *
 * @param correctPositions the positions of the letters that are in the correct position
 * @param presentLetters the positions of the letters that are present in the word
 */
public record WordHints(List<Integer> correctPositions, List<Integer> presentLetters)
        implements Serializable {

}
