package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import model.User;
import model.WordHints;
import org.jetbrains.annotations.NotNull;

/**
 * Play Wordle service.
 */
public class PlayWordleService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ConcurrentHashMap<User, List<String>> playedGames;

    /**
     * Constructor for PlayWordleService.
     */
    public PlayWordleService() {
        this.playedGames = getPlayedGames();
    }

    /**
     * Gets the list of all already played games.
     *
     * @return the map of all already played games
     */
    private synchronized ConcurrentHashMap<User, List<String>> getPlayedGames() {
        try (final JsonReader reader = new JsonReader(
                new FileReader("src/main/java/server/conf/playedGames.json"))) {
            ConcurrentHashMap<User, List<String>> games;
            games = gson.fromJson(
                    reader, new TypeToken<ConcurrentHashMap<User, List<String>>>() {
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
     * @param user the user
     * @param word the word
     * @return true if the user has played the word
     */
    public synchronized boolean hasPlayed(@NotNull User user, @NotNull String word) {
        if (!playedGames.containsKey(user)) {
            return false;
        }
        return playedGames.get(user).contains(word);
    }


    /**
     * Guesses the word.
     *
     * @param word the word to guess
     * @return the hints
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
     * Adds a played game to the list of played games.
     *
     * @param user the user
     * @param word the word
     */
    public synchronized boolean addPlayedGame(@NotNull User user, @NotNull String word) {
        if (!playedGames.containsKey(user)) {
            playedGames.put(user, new ArrayList<>());
        }
        playedGames.get(user).add(word);
        System.out.println("Added played game for " + user.getUsername() + " for word " + word);
        try (final FileWriter writer =
                     new FileWriter("src/main/java/server/conf/playedGames.json")) {
            gson.toJson(playedGames, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }


}
