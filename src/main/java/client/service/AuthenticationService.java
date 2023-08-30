package client.service;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Singleton service to manage the client-side authentication of a user.
 */
public class AuthenticationService {

    private static AuthenticationService instance;
    private Optional<String> loggedUser;


    private AuthenticationService() {
        this.loggedUser = Optional.empty();
    }


    /**
     * Gets the instance of the AuthenticationService.
     *
     * @return the instance
     */
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public Optional<String> getLoggedUser() {
        return loggedUser;
    }

    /**
     * Logins a user.
     *
     * @param username the username of the user that should be logged in
     */
    public void login(@NotNull String username) {
        this.loggedUser = Optional.of(username);
    }

    /**
     * Logouts a user.
     */
    public void logout() {
        this.loggedUser = Optional.empty();
    }

}
