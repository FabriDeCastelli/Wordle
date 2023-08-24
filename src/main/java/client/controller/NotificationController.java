package client.controller;


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
 */
public class NotificationController extends Thread {

    public final String multicastIp;
    public final int multicastPort;
    public final Queue<Notification> notifications = new ConcurrentLinkedQueue<>();

    /**
     * Constructor for the NotificationController.
     *
     * @param multicastIp   the multicast IP
     * @param multicastPort the multicast port
     */
    public NotificationController(String multicastIp, int multicastPort) {
        this.multicastIp = multicastIp;
        this.multicastPort = multicastPort;
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

                notification.ifPresent(notifications::add);
                System.out.println("Received notification.");

                objectInputStream.close();

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
