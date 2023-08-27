package server.service;

import static server.service.UserServiceManager.gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import model.User;
import model.UserStatistics;
import org.jetbrains.annotations.NotNull;


/**
 * User statistics service.
 */
public class UserStatisticsService {

    private final String filePath;
    private final ConcurrentHashMap<String, User> userStore;


    /**
     * Constructor for the UserStatisticsService.
     */
    public UserStatisticsService() {
        this.filePath = "src/main/java/server/conf/users.json";
        this.userStore = UserServiceManager.getInstance(filePath).getUsersMap();
    }

    /**
     * Constructor for the UserStatisticsService.
     *
     * @param filePath the file path of the store
     */
    public UserStatisticsService(String filePath) {
        this.filePath = filePath;
        this.userStore = UserServiceManager.getInstance(filePath).getUsersMap();
    }

    /**
     * Gets the user statistics with the given username.
     *
     * @param username the username
     * @param userStatistics the user statistics
     * @return true for success and false for failure
     */
    public synchronized boolean updateStatistics(
            @NotNull String username, UserStatistics userStatistics) {
        final User user = userStore.get(username);
        if (user == null) {
            throw new IllegalStateException("User not found when updating statistics");
        }
        user.setUserStatistics(userStatistics);
        userStore.put(username, user);
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userStore, writer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Gets the user statistics with the given username.
     *
     * @param username the username
     * @return the user statistics for that user
     */
    public synchronized UserStatistics getStatisticsByUsername(@NotNull String username) {
        final User user = userStore.get(username);
        if (user == null) {
            throw new IllegalStateException("User not found when getting statistics");
        }
        return user.getUserStatistics();
    }


}
