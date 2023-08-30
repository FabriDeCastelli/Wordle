package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import server.controller.RequestHandler;
import server.controller.TerminationHandler;
import server.service.WordExtractionService;

/**
 * Communicates with WordleClientMain through a Persistent TCP connection.
 */
public class WordleServerMain {

    public static String multicastIp;
    public static int multicastPort;
    public static int port;
    public static int wordDuration;

    /*
     * Static block to initialize the properties, as instance-independent.
     */
    static {
        try (final InputStream inputStream =
                     new FileInputStream("src/main/java/server/conf/server.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            port = Integer.parseInt(properties.getProperty("PORT"));
            wordDuration = Integer.parseInt(properties.getProperty("WORDDURATION"));
            multicastIp = properties.getProperty("MULTICAST_IP");
            multicastPort = Integer.parseInt(properties.getProperty("MULTICAST_PORT"));
        } catch (IOException e) {
            System.out.println("Error reading properties file.");
        }

    }

    /**
     * Main method for the WordleServer.
     * Opens a ServerSocket and a MulticastSocket.
     * Creates a thread pool to handle the requests and a thread pool to extract a new word.
     */
    public static void main(String[] args) {


        try (final ServerSocket server = new ServerSocket(port);
             final MulticastSocket multicastSocket = new MulticastSocket()) {

            final ExecutorService executorService = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(
                    new TerminationHandler(2000, executorService, server, multicastSocket)
            );

            final ScheduledExecutorService wordExtractionService =
                    Executors.newSingleThreadScheduledExecutor();
            wordExtractionService.scheduleAtFixedRate(
                    new WordExtractionService(), 0, wordDuration, TimeUnit.MINUTES
            );

            while (true) {
                executorService.execute(new RequestHandler(server.accept(), multicastSocket));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}

