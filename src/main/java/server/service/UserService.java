package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
import org.jetbrains.annotations.NotNull;

/**
 * Service for user related operations.
 */
public class UserService {

    protected final String filePath;
    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected final ConcurrentHashMap<String, User> users;

    public UserService(String filePath) {
        this.filePath = filePath;
        this.users = UserServiceManager.getInstance(filePath).getUsersMap();
    }


    /**
     * Gets the user with the given getUsername.
     *
     * @param username the getUsername.
     */
    public synchronized Optional<User> getUserByUsername(@NotNull String username) {
        return Optional.ofNullable(users.get(username));
    }

}
