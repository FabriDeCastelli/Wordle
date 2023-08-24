package server.service.command;

import java.net.MulticastSocket;
import model.Notification;
import model.Request;
import model.Response;
import model.StreamHandler;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;

/**
 * Represents a share command.
 */
public class ShareCommand implements Command {

    private final MulticastSocket multicastSocket;

    public ShareCommand(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SHARE) {
            throw new IllegalArgumentException("Cannot handle a non-share requestType");
        } else if (request.data() == null) {
            throw new IllegalArgumentException("Cannot share a null statistics");
        }

        final Notification notification = new Notification(
                request.username() + " shared a result!",
                request.username(),  request.data());

        return StreamHandler.sendMulticastData(multicastSocket, notification)
                ? new Response(Status.SUCCESS, "Statistics shared successfully.")
                : new Response(Status.FAILURE, "Statistics could not be shared.");

    }

}
