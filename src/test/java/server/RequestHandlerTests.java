package server;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * RequestHandlerTests is the test suite for RequestHandler.
 */
@DisplayName("The request handler tests ")
public class RequestHandlerTests {

    @Mock
    private ObjectInputStream in;
    @Mock
    private ObjectOutputStream out;
    @Mock
    private Socket socket;
    private RequestHandler requestHandler;

    /**
     * Sets up the test suite.
     */
    @BeforeEach
    public void setUp() throws Exception {
        in = mock(ObjectInputStream.class);
        out = mock(ObjectOutputStream.class);
        socket = mock(Socket.class);
        requestHandler = new RequestHandler(socket, in, out);
    }


    @Test
    @DisplayName(" can correctly handle a user request")
    public void testHandleUserRequest() {
        // TODO

    }

    /**
     * Tears down the test suite.
     *
     * @throws IOException if an I/O error occurs when closing resources.
     */
    @AfterEach
    public void tearDown() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}
