package server.service;

import static server.service.UserServiceManager.gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
import org.jetbrains.annotations.NotNull;

/**
 * Service that manages the authentication of the users.
 * Stores the logged user for the current session
 */
public class AuthenticationService {

    private static AuthenticationService instance;
    private final List<String> loggedUsers;
    private final ConcurrentHashMap<String, User> userStore;
    private final String filePath;


    /**
     * Constructor for the AuthenticationService.
     *
     * @param filePath the file path of the json file
     */
    private AuthenticationService(String filePath) {
        this.filePath = filePath;
        this.loggedUsers = Collections.synchronizedList(new ArrayList<>());
        this.userStore = UserServiceManager.getInstance(filePath).getUsersMap();
    }


    /**
     * Gets the instance of the AuthenticationService.
     *
     * @param filePath the file path of the store
     * @return the instance of the AuthenticationService
     */
    public static AuthenticationService getInstance(@NotNull String filePath) {
        if (instance == null) {
            instance = new AuthenticationService(filePath);
        }
        return instance;
    }

    /**
     * Adds a user to the list of logged users and sets it as the logged user.
     *
     * @param user the user to be logged in
     * @return true if it was added, false otherwise
     */
    public synchronized boolean login(@NotNull User user) {
        if (loggedUsers.contains(user.getUsername())) {
            return false;
        }
        return this.loggedUsers.add(user.getUsername());
    }

    /**
     * Removes a user from the list of logged users.
     *
     * @return true if it was removed, false otherwise
     */
    public synchronized boolean logout(@NotNull String username) {
        return loggedUsers.remove(username);
    }


    /**
     * Removes a user from the list of registered users.
     * Used for testing purposes.
     *
     * @param user the user to be deleted
     */
    synchronized boolean unregister(@NotNull User user) {

        if (!isRegistered(user.getUsername())) {
            return false;
        }

        userStore.remove(user.getUsername());
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userStore, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    /**
     * Saves a new user the json file.
     *
     * @param user the user to be added
     */
    public synchronized boolean register(@NotNull User user) {

        if (isRegistered(user.getUsername())) {
            return false;
        }
        userStore.put(user.getUsername(), user);
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userStore, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    /**
     * Gets the user with the given getUsername.
     *
     * @param username the username of the user.
     */
    public synchronized Optional<User> getRegisteredUserByUsername(@NotNull String username) {
        return Optional.ofNullable(userStore.get(username));
    }

    /**
     * Checks if a user with the given username is registered.
     *
     * @param username the username of the user.
     */
    public boolean isRegistered(@NotNull String username) {
        return getRegisteredUserByUsername(username).isPresent();
    }


}