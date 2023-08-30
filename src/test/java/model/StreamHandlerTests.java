package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for StreamHandler class.
 */
@DisplayName("The StreamHandler Tests")
public class StreamHandlerTests {


    @Test
    @DisplayName(" can correctly send data through a stream")
    void testSendData() {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)) {

            assertTrue(StreamHandler.sendData(objectOutputStream, "Hi"));

        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName(" can correctly get data from a stream")
    void testGetDataSuccess() {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)) {

            StreamHandler.sendData(objectOutputStream, "Hello, World!");

            final ByteArrayInputStream byteInputStream =
                    new ByteArrayInputStream(byteOutputStream.toByteArray());
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);

            final Optional<String> data = StreamHandler.getData(objectInputStream, String.class);
            assertTrue(data.isPresent());
            assertEquals("Hello, World!", data.get());
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName(" correctly rejects data of wrong type in streams")
    void testGetDataFailure() {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)) {

            StreamHandler.sendData(objectOutputStream, "Hello, World!");

            final ByteArrayInputStream byteInputStream =
                    new ByteArrayInputStream(byteOutputStream.toByteArray());
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);

            final Optional<Integer> data = StreamHandler.getData(objectInputStream, Integer.class);
            assertFalse(data.isPresent());
        } catch (IOException e) {
            fail("Exception:" + e.getMessage());
        }
    }

    @Test
    @DisplayName(" can correctly send data through a multicast socket")
    void testSendMulticastData() {
        try (final MulticastSocket multicastSocket = new MulticastSocket()) {
            assertTrue(StreamHandler.sendMulticastData(multicastSocket, "Hi"));
        } catch (IOException e) {
            fail("Exception: " + e.getMessage());
        }
    }

}
