package server.controller;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the TerminationHandler class.
 */
@DisplayName("The termination handler test ")
public class TerminationHandlerTests {

    private static int maximumDelay;
    private static ExecutorService executorService;
    private static ServerSocket serverSocket;
    private static MulticastSocket multicastSocket;

    @BeforeAll
    static void setup() throws IOException {
        maximumDelay = 0;
        executorService = Executors.newCachedThreadPool();
        serverSocket = new ServerSocket(9999);
        multicastSocket = new MulticastSocket();
    }

    @Test
    @DisplayName(" can correctly create a TerminationHandler")
    public void testTerminationHandler() {
        new TerminationHandler(maximumDelay, executorService, serverSocket, multicastSocket);
    }

    @Test
    @DisplayName(" can correctly run a thread")
    public void testRun() {
        final TerminationHandler terminationHandler =
                new TerminationHandler(
                        maximumDelay, executorService, serverSocket, multicastSocket);
        terminationHandler.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        serverSocket.close();
        multicastSocket.close();
    }

}
