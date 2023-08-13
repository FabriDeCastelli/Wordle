package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
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



}
