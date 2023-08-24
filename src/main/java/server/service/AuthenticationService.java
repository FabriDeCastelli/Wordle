package server.service;

import java.io.FileWriter;
import java.io.IOException;
import model.User;
import org.jetbrains.annotations.NotNull;

/**
 * Service for authenticating users.
 */
public class AuthenticationService extends UserService {


    /**
     * Constructor for the AuthenticationService.
     *
     * @param filePath the file path of the json file
     */
    public AuthenticationService(String filePath) {
        super(filePath);
    }

    public AuthenticationService() {
        super("src/main/java/server/conf/users.json");
    }


    /**
     * Saves a new user the json file.
     *
     * @param user the user to be added
     */
    public synchronized boolean registerUser(@NotNull User user) {

        if (getRegisteredUserByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        users.put(user.getUsername(), user);
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
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
    public synchronized boolean deleteRegistration(@NotNull User user) {
        if (getRegisteredUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete a user that is not registered.");
        }

        users.remove(user.getUsername());
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    /**
     * Adds a user to the list of logged users.
     *
     * @param username the username of the user to be added
     * @return true if it was added, false otherwise
     */
    public synchronized boolean addToLoggedUsers(@NotNull String username) {
        if (getLoggedUserByUsername(username).isPresent()) {
            return false;
        }
        return loggedUsers.add(username);
    }

    /**
     * Removes a user from the list of logged users.
     *
     * @param username the username of the user to be added
     * @return true if it was removed, false otherwise
     */
    public synchronized boolean removeFromLoggedUsers(@NotNull String username) {
        if (getLoggedUserByUsername(username).isEmpty()) {
            return false;
        }
        return loggedUsers.remove(username);
    }


}