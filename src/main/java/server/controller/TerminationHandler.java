package server.controller;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Handles the termination of the server.
 */
public class TerminationHandler extends Thread {
    private final int maximumDelay;
    private final ExecutorService executorService;
    private final ServerSocket serverSocket;
    private final MulticastSocket multicastSocket;

    /**
     * Constructor for the TerminationHandler.
     *
     * @param maximumDelay    the maximum delay
     * @param executorService the executor service
     * @param serverSocket    the server socket
     */
    public TerminationHandler(
            int maximumDelay,
            ExecutorService executorService,
            ServerSocket serverSocket,
            MulticastSocket multicastSocket) {
        this.maximumDelay = maximumDelay;
        this.executorService = executorService;
        this.serverSocket = serverSocket;
        this.multicastSocket = multicastSocket;
    }

    @Override
    public void run() {
        
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("TerminationHandler error: failed to stop server.");
            }

        }

        if (multicastSocket != null && !multicastSocket.isClosed()) {
            multicastSocket.close();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(maximumDelay, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }


}
