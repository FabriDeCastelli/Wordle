package server.service;

import static server.service.UserService.gson;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

/**
 * Singleton class to share users' data.
 */
public class UserServiceManager {

    private final String filePath;
    private static UserServiceManager instance;
    private final ConcurrentHashMap<String, User> users;

    /**
     * Constructor for UserServiceManager.
     */
    private UserServiceManager(String filePath) {
        this.filePath = filePath;
        users = getUsersData();
    }

    /**
     * Gets the instance of the UserServiceManager.
     *
     * @return the instance of the UserServiceManager
     */
    public static UserServiceManager getInstance(String filePath) {
        if (instance == null) {
            instance = new UserServiceManager(filePath);
        }
        return instance;
    }

    /**
     * Gets the list of all already registered users.
     *
     * @return the map of all already registered users
     */
    private synchronized ConcurrentHashMap<String, User> getUsersData() {
        try (final JsonReader reader = new JsonReader(new FileReader(filePath))) {
            final ConcurrentHashMap<String, User> users = gson.fromJson(
                    reader, new TypeToken<ConcurrentHashMap<String, User>>() {
                    }.getType());
            return users == null ? new ConcurrentHashMap<>() : users;
        } catch (IOException e) {
            System.out.println("Error getting all users.");
        }
        return new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, User> getUsersMap() {
        return users;
    }

}
