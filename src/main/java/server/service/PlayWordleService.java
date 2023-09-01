package server.service;

import static server.service.UserServiceManager.gson;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.WordHints;
import org.jetbrains.annotations.NotNull;

/**
 * Service that manages the game logic.
 */
public class PlayWordleService {

    private final ConcurrentHashMap<String, List<String>> playedGames;
    private final String filePath;

    /**
     * Constructor for PlayWordleService.
     */
    public PlayWordleService() {
        this.filePath = "src/main/java/server/config/playedGames.json";
        this.playedGames = getPlayedGames();
    }


    /**
     * Gets the list of all already played games.
     *
     * @return the map of all already played games
     */
    private synchronized ConcurrentHashMap<String, List<String>> getPlayedGames() {
        try (final JsonReader reader = new JsonReader(
                new FileReader(filePath))) {
            final ConcurrentHashMap<String, List<String>> games = gson.fromJson(
                    reader, new TypeToken<ConcurrentHashMap<String, List<String>>>() {
                    }.getType());
            return games == null ? new ConcurrentHashMap<>() : games;
        } catch (IOException e) {
            System.out.println("Error getting all played games.");
        }
        return new ConcurrentHashMap<>();

    }

    /**
     * Determines whether a user has already played the Wordle game for that word.
     *
     * @param username the username of the user that wants to play the game
     * @param word the word of the current game
     * @return true if the user has played the game for that word, false otherwise
     */
    public synchronized boolean hasPlayed(@NotNull String username, @NotNull String word) {
        final List<String> playedWords = playedGames.get(username);
        return playedWords != null && playedWords.contains(word);
    }


    /**
     * Guesses the word.
     *
     * @param word the word to be guessed
     * @return the hints for the word as two lists of integers
     */
    public WordHints guessWord(@NotNull String word) {
        final String currentWord = WordExtractionService.getCurrentWord();
        if (word.length() != currentWord.length()) {
            throw new IllegalStateException("Word must be the same length as the current word.");
        }

        final List<Integer> correctPositions = IntStream.range(0, word.length())
                .filter(i -> word.charAt(i) == currentWord.charAt(i))
                .boxed()
                .collect(Collectors.toList());

        final List<Integer> presentLetters = IntStream.range(0, word.length())
                .filter(i -> word.charAt(i) != currentWord.charAt(i)
                        && currentWord.contains(String.valueOf(word.charAt(i))))
                .boxed()
                .collect(Collectors.toList());

        return new WordHints(correctPositions, presentLetters);
    }

    /**
     * Adds a played game to the list of played games and save it to the store.
     *
     * @param username the username of the user that played the game
     * @param word the word to be added
     */
    public synchronized boolean addPlayedGame(@NotNull String username,  @NotNull String word) {
        if (!playedGames.containsKey(username)) {
            playedGames.put(username, new ArrayList<>());
        }
        playedGames.get(username).add(word);
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(playedGames, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }


}
