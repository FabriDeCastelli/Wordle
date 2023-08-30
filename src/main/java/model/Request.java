package model;

import client.service.AuthenticationService;
import java.io.Serializable;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a request from client to server.
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
