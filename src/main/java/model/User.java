package model;

import java.io.Serial;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a user.
 * A user has a username (assumed to be unique), a hashed password and its statistics.
 */
public final class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;
    private String passwordHash;
    private UserStatistics statistics;


    /**
     * Constructor for the User.
     *
     * @param username     the username
     * @param passwordHash the hashed password
     */
    public User(@NotNull String username, @NotNull String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.statistics = new UserStatistics();
    }

    /**
     * Constructor for the User, used for authentication.
     *
     * @param authDTO the authDTO
     */
    public User(@NotNull AuthDTO authDTO) {
        this.username = authDTO.username();
        this.passwordHash = authDTO.password();
        this.statistics = new UserStatistics();
    }


    /**
     * Getter for the getUsername.
     *
     * @return the getUsername
     */
    public String getUsername() {
        return username;
    }

    public UserStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(UserStatistics userStatistics) {
        this.statistics = userStatistics;
    }


    /**
     * Getter for the getPasswordHash.
     *
     * @return the getPasswordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof User user) {
            return this.username.equals(user.username)
                    && this.passwordHash.equals(user.passwordHash);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode() + this.passwordHash.hashCode();
    }

}
