package model;

import client.service.AuthenticationService;
import java.io.Serializable;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a request that a client can do to the server.
 *
 * @param username the username of the user that is doing the request
 * @param requestType the type of the request the client wants to be satisfied
 * @param data the data of the request, if any
 */
public record Request(
        @NotNull RequestType requestType,
        String username,
        Object data) implements Serializable {

    private static final AuthenticationService authenticationService =
            AuthenticationService.getInstance();

    /**
     * Constructor for the Request.
     *
     * @param requestType the request type
     */
    public Request(RequestType requestType) {
        this(
                requestType,
                authenticationService.getLoggedUser().orElse(null),
                null
        );
    }


    /**
     * Constructor for the Request.
     *
     * @param requestType the request type
     * @param data the data
     */
    public Request(RequestType requestType, Object data) {
        this(
                requestType,
                authenticationService.getLoggedUser().orElse(null),
                data
        );
    }


}
