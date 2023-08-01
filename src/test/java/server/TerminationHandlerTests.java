package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TerminationHandlerTests is the test suite for TerminationHandler.
 */
@DisplayName("The termination handler test ")
public class TerminationHandlerTests {

    private static int maximumDelay;
    private static ExecutorService executorService;
    private static ServerSocket serverSocket;

    @BeforeAll
    static void setup() throws IOException {
        maximumDelay = 0;
        executorService = Executors.newCachedThreadPool();
        serverSocket = new ServerSocket(9999);

    }

    @Test
    @DisplayName(" can correctly create a TerminationHandler")
    public void testTerminationHandler() {
        new TerminationHandler(maximumDelay, executorService, serverSocket);
    }

    @Test
    @DisplayName(" can correctly run a thread")
    public void testRun() {
        TerminationHandler terminationHandler =
                new TerminationHandler(maximumDelay, executorService, serverSocket);
        terminationHandler.run();
    }

    @AfterAll
    static void tearDown() throws IOException {
        serverSocket.close();
    }

}
