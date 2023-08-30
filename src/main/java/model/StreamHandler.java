package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import server.WordleServerMain;


/**
 * StreamHandler is a utility class for read and writing objects to streams.
 */
public class StreamHandler {

    /**
     * Reads an object from an input stream.
     *
     * @param in the input stream
     * @param <T> the type of the read object
     * @return an optional object of type T
     */
    public static <T> Optional<T> getData(ObjectInputStream in, Class<T> clazz) {
        try {
            final Object obj = in.readObject();
            if (obj.getClass() == clazz) {
                return Optional.of(clazz.cast(obj));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Stream closed");
        }
        return Optional.empty();
    }


    /**
     * Writes an object to an output stream.
     *
     * @param out the output stream
     * @param data the object to be written
     * @return true if the object was written successfully
     */
    public static <T> boolean sendData(ObjectOutputStream out, T data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Sends a request to a socket and returns the response.
     *
     * @param out       the output stream
     * @param in        the input stream
     * @param request   the request to be sent
     * @return          the response from the server
     */
    public static Optional<Response> sendAndGetResponse(
            @NotNull ObjectOutputStream out,
            @NotNull ObjectInputStream in,
            @NotNull Request request) {
        return sendData(out, request)
                ? getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends an object of generic type to a multicast socket.
     *
     * @param multicastSocket the multicast socket
     * @param data the object to be sent
     * @return true if the object was sent successfully
     */
    public static <T> boolean sendMulticastData(MulticastSocket multicastSocket, T data) {
        try {
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();

            byte[] dataBytes = byteStream.toByteArray();
            final InetAddress inetAddress = InetAddress.getByName(WordleServerMain.multicastIp);
            final DatagramPacket packet = new DatagramPacket(
                    dataBytes, dataBytes.length,
                    inetAddress,
                    WordleServerMain.multicastPort);
            multicastSocket.send(packet);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}