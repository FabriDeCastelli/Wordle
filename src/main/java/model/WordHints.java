package model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the hints for a word.
 */
public record WordHints(List<Integer> correctPositions, List<Integer> presentLetters)
        implements Serializable {

}
