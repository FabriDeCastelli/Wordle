package model;

import java.io.Serializable;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a user requestType.
 */
public record Request(
        @NotNull RequestType requestType,
        Object data) implements Serializable {

    /**
     * Constructor for the Request.
     *
     * @param requestType the request type
     */
    public Request(RequestType requestType) {
        this(requestType, null);
    }


}
