package client.service;

import client.controller.MulticastTerminationHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import model.Notification;
import model.StreamHandler;

/**
 * Controller for the notifications.
 * Collects the notification received from the multicast socket and filters them.
 */
public class NotificationService extends Thread {

    public final String multicastIp;
    public final int multicastPort;
    public final String username;
    public static final Queue<Notification> notifications = new ConcurrentLinkedQueue<>();

    /**
     * Constructor for the NotificationService.
     *
     * @param username      the username of the owner of the notifications
     * @param multicastIp   the multicast IP
     * @param multicastPort the multicast port
     */
    public NotificationService(String username, String multicastIp, int multicastPort) {
        this.multicastIp = multicastIp;
        this.multicastPort = multicastPort;
        this.username = username;
    }

    @Override
    public void run() {
        try (final MulticastSocket multicastSocket = new MulticastSocket(multicastPort)) {
            final InetSocketAddress inetSocketAddress =
                    new InetSocketAddress(multicastIp, multicastPort);
            final NetworkInterface networkInterface = NetworkInterface.getByName("en0");
            multicastSocket.joinGroup(inetSocketAddress, networkInterface);

            Runtime.getRuntime().addShutdownHook(new MulticastTerminationHandler(
                    multicastSocket, inetSocketAddress, networkInterface));

            while (true) {

                final byte[] buffer = new byte[1024];
                final DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(datagramPacket);

                final ByteArrayInputStream byteStream =
                        new ByteArrayInputStream(buffer, 0, datagramPacket.getLength());
                final ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);

                final Optional<Notification> notification =
                        StreamHandler.getData(objectInputStream, Notification.class);
                objectInputStream.close();

                notification.filter(n -> !n.senderUsername().equals(username))
                        .ifPresent(notifications::add);


            }

        } catch (IOException e) {
            System.out.println("Could not receive multicast packet");
        }
    }


}
