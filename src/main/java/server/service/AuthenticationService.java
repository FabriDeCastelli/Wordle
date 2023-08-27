package server.service;

import static server.service.UserServiceManager.gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
import org.jetbrains.annotations.NotNull;

/**
 * Service for authenticating users.
 */
public class AuthenticationService {

    private Optional<User> loggedUser;
    private final List<String> loggedUsers;
    private final ConcurrentHashMap<String, User> userStore;
    private final String filePath;


    /**
     * Constructor for the AuthenticationService.
     *
     * @param filePath the file path of the json file
     */
    public AuthenticationService(String filePath) {
        this.filePath = filePath;
        this.loggedUser = Optional.empty();
        this.loggedUsers = UserServiceManager.getInstance(filePath).getLoggedUsers();
        this.userStore = UserServiceManager.getInstance(filePath).getUsersMap();
    }

    /**
     * Constructor for the AuthenticationService.
     */
    public AuthenticationService() {
        this.filePath = "src/main/java/server/conf/users.json";
        this.loggedUser = Optional.empty();
        loggedUsers = UserServiceManager.getInstance(filePath).getLoggedUsers();
        userStore = UserServiceManager.getInstance(filePath).getUsersMap();
    }


    /**
     * Adds a user to the list of logged users.
     *
     * @param user the user to be logged in
     * @return true if it was added, false otherwise
     */
    public synchronized boolean login(@NotNull User user) {
        if (loggedUsers.contains(user.getUsername())) {
            return false;
        }
        this.loggedUser = Optional.of(user);
        return this.loggedUsers.add(user.getUsername());
    }

    /**
     * Removes a user from the list of logged users.
     *
     * @return true if it was removed, false otherwise
     */
    public synchronized boolean logout() {
        final User loggedUser = this.loggedUser
                .orElseThrow(() -> new IllegalArgumentException("Cannot logout a not logged user"));
        return loggedUsers.remove(loggedUser.getUsername());
    }

    public synchronized Optional<User> getLoggedUser() {
        return loggedUser;
    }

    /**
     * Removes a user from the list of registered users.
     *
     * @param user the user to be deleted
     */
    public synchronized boolean unregister(@NotNull User user) {

        getRegisteredUserByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
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

        if (getRegisteredUserByUsername(user.getUsername()).isPresent()) {
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


}