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
    public synchronized boolean add(@NotNull User user) {

        if (getUserByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("The user is already stored.");
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
    public synchronized boolean delete(@NotNull User user) {
        if (getUserByUsername(user.getUsername()).isEmpty()) {
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

}