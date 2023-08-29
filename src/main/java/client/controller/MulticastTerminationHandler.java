package client.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for handling the termination of the multicast.
 */
public class MulticastTerminationHandler extends Thread {

    private final MulticastSocket multicastSocket;
    private final InetSocketAddress inetSocketAddress;
    private final NetworkInterface networkInterface;

    /**
     * Constructor for the MulticastTerminationHandler.
     *
     * @param multicastSocket the multicast socket to close
     * @param inetSocketAddress the multicast socket address
     * @param networkInterface the network interface
     */
    public MulticastTerminationHandler(
            @NotNull MulticastSocket multicastSocket,
            @NotNull InetSocketAddress inetSocketAddress,
            @NotNull NetworkInterface networkInterface) {
        this.multicastSocket = multicastSocket;
        this.inetSocketAddress = inetSocketAddress;
        this.networkInterface = networkInterface;

    }

    @Override
    public void run() {
        try {
            if (!multicastSocket.isClosed()) {
                multicastSocket.leaveGroup(inetSocketAddress, networkInterface);
                multicastSocket.close();
                System.out.println("Multicast correctly closed.");
            }
        } catch (IOException e) {
            System.out.println("Error closing multicast socket");
        }
    }
}
