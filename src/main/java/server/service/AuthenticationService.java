package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
import org.jetbrains.annotations.NotNull;

/**
 * Service for authenticating users.
 */
public class AuthenticationService {

    private final String filePath = "src/main/java/server/conf/users.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ConcurrentHashMap<String, User> registeredUsers;


    /**
     * Constructor for the AuthenticationService.
     */
    public AuthenticationService() {
        this.registeredUsers = getRegisteredUsers();
    }


    /**
     * Gets the list of all already registered users.
     *
     * @return the map of all already registered users
     */
    private synchronized ConcurrentHashMap<String, User> getRegisteredUsers() {
        try (final JsonReader reader = new JsonReader(new FileReader(filePath))) {
            ConcurrentHashMap<String, User> users;
            users = gson.fromJson(
                    reader, new TypeToken<ConcurrentHashMap<String, User>>() {}.getType());
            return users == null ? new ConcurrentHashMap<>() : users;
        } catch (IOException e) {
            System.out.println("Error getting all users.");
        }
        return new ConcurrentHashMap<>();
    }

    /**
     * Gets the user with the given getUsername.
     *
     * @param username the getUsername.
     */
    public synchronized Optional<User> getUserByUsername(@NotNull String username) {
        return Optional.ofNullable(getRegisteredUsers().get(username));
    }

    /**
     * Saves a new user the json file.
     *
     * @param user the user to be added
     */
    public synchronized boolean add(User user) {

        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null.");
        } else if (getUserByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("The user is already stored.");
        }

        this.registeredUsers.put(user.getUsername(), user);
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(registeredUsers, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    /**
     * Removes a user from the list of registered users.
     *
     * @param user the user to be deleted
     */
    public synchronized boolean delete(@NotNull User user) {
        if (getUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete a user that is not registered.");
        }

        this.registeredUsers.remove(user.getUsername());
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(registeredUsers, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }






}
