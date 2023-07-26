package client;

/**
 * WordleClient is the client for the Wordle game.
 * It is responsible for connecting to the server and
 * sending and receiving messages.
 */
public class WordleClient {

    private final int id;

    /**
     * Constructor for WordleClient.
     *
     * @param id The id of the client.
     */
    public WordleClient(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}