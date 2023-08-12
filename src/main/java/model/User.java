package model;

import java.io.Serial;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a user.
 * A user has a getUsername and a getPasswordHash, with the getUsername assumed to be unique.
 */
public final class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String passwordHash;


    /**
     * Constructor for the User.
     *
     * @param username     the getUsername
     * @param passwordHash the getPasswordHash
     */
    public User(@NotNull String username, @NotNull String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Getter for the getUsername.
     *
     * @return the getUsername
     */
    public String getUsername() {
        return username;
    }


    /**
     * Getter for the getPasswordHash.
     *
     * @return the getPasswordHash
     */
    public String getPasswordHash() {
        return passwordHash;
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
