package server.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import model.User;
import model.UserStatistics;
import org.jetbrains.annotations.NotNull;

/**
 * User statistics service.
 */
public class UserStatisticsService extends UserService {

    /**
     * Constructor for the UserStatisticsService.
     */
    public UserStatisticsService() {
        super("src/main/java/server/conf/users.json");
    }

    /**
     * Constructor for the UserStatisticsService.
     *
     * @param filePath the file path
     */
    public UserStatisticsService(String filePath) {
        super(filePath);
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
        final Optional<User> user = getRegisteredUserByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Cannot update statistics for a not stored user");
        }

        user.get().setUserStatistics(userStatistics);
        users.put(username, user.get());
        try (final FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
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

        final Optional<User> user = getRegisteredUserByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Cannot get statistics for a not stored user");
        }
        return user.get().getUserStatistics();
    }


}
