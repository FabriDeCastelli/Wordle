package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

/**
 * Singleton class to share users' across services.
 */
public class UserServiceManager {

    private final String filePath;
    private static UserServiceManager instance;
    private final ConcurrentHashMap<String, User> users;
    private final List<String> loggedUsers;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Constructor for UserServiceManager.
     */
    private UserServiceManager(String filePath) {
        this.filePath = filePath;
        this.users = getUsersData();
        this.loggedUsers = Collections.synchronizedList(new ArrayList<>());
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
            throw new RuntimeException("Error getting all registered users.");
        }
    }

    public ConcurrentHashMap<String, User> getUsersMap() {
        return users;
    }

    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

}
