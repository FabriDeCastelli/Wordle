package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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


    /**
     * Main method for the WordleServer.
     */
    @SuppressWarnings("PMD")
    public static void main(String[] args) {

        final int port;
        final int wordDuration;
        try (final InputStream inputStream =
                     new FileInputStream("src/main/java/server/conf/server.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            port = Integer.parseInt(properties.getProperty("PORT"));
            wordDuration = Integer.parseInt(properties.getProperty("WORDDURATION"));
        } catch (IOException e) {
            System.out.println("Error reading properties file.");
            return;
        }

        try (ServerSocket server = new ServerSocket(port)) {

            final ExecutorService executorService = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(
                    new TerminationHandler(2000, executorService, server)
            );

            final ScheduledExecutorService wordExtractionService =
                    Executors.newSingleThreadScheduledExecutor();
            wordExtractionService.scheduleAtFixedRate(
                    new WordExtractionService(), 0, wordDuration, TimeUnit.MINUTES
            );

            while (true) {
                executorService.execute(new RequestHandler(server.accept()));
            }

        } catch (IOException e) {
            System.out.println("Error creating socket.");
        }

    }



}
