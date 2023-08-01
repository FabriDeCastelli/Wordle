package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Communicates with WordleClientMain through a Persistent TCP connection.
 */
public class WordleServerMain {

    private static int PORT;

    /**
     * Main method for the WordleServer.
     */
    @SuppressWarnings("PMD")
    public static void main(String[] args) {

        try (final InputStream inputStream =
                     new FileInputStream("src/main/java/server/conf/server.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            PORT = Integer.parseInt(properties.getProperty("PORT"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (ServerSocket server = new ServerSocket(PORT)) {

            final ExecutorService executorService = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(
                    new TerminationHandler(2000, executorService, server)
            );

            while (true) {
                final Socket socket = server.accept();
                final ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                final RequestHandler requestHandler = new RequestHandler(socket, in, out);
                executorService.execute(requestHandler);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
