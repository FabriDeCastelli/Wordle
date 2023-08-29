package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a game result.
 */
public record GameResult(@NotNull List<WordHints> wordHintsHistory,
                         @NotNull UserStatistics userStatistics) implements Serializable {

    @Serial
    private static final long serialVersionUID = 987654321L;


}
