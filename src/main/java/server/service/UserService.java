package server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
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
    protected final List<String> loggedUsers;

    /**
     * Constructor for the UserService.
     *
     * @param filePath the file path of the json file
     */
    public UserService(String filePath) {
        this.filePath = filePath;
        this.users = UserServiceManager.getInstance(filePath).getUsersMap();
        this.loggedUsers = UserServiceManager.getInstance(filePath).getLoggedUsers();
    }


    /**
     * Gets the user with the given getUsername.
     *
     * @param username the username of the user.
     */
    public synchronized Optional<User> getRegisteredUserByUsername(@NotNull String username) {
        return Optional.ofNullable(users.get(username));
    }

    /**
     * Gets the user with the given getUsername.
     *
     * @param username the username of the user.
     * @return the username of the user if present, empty otherwise.
     */
    public synchronized Optional<String> getLoggedUserByUsername(@NotNull String username) {
        return loggedUsers.stream().filter(u -> u.equals(username)).findFirst();
    }

}
