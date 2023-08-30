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
 * Shares a result with the other users in the multicast socket.
 */
public class ShareCommand implements Command {

    private final MulticastSocket multicastSocket;

    public ShareCommand(@NotNull MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SHARE) {
            throw new IllegalArgumentException("Cannot handle a non-SHARE request");
        } else if (request.data() == null) {
            throw new IllegalArgumentException("Cannot share a null result");
        }

        final String username = request.username();

        final Notification notification =
                new Notification(username + " shared a result!", username,  request.data());

        return StreamHandler.sendMulticastData(multicastSocket, notification)
                ? new Response(Status.SUCCESS, "Statistics shared successfully.")
                : new Response(Status.FAILURE, "Statistics could not be shared.");

    }

}
