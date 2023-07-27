package client;

import gui.AuthenticationPage;

/**
 * WordleClient is the client for the Wordle game.
 * It is responsible for connecting to the server and
 * sending and receiving messages.
 */
public class WordleClient {

    /**
     * Constructor for the WordleClient.
     */
    public WordleClient() {

    }

    /**
     * Main method for the WordleClient.
     */
    public static void main(String[] args) {
        new AuthenticationPage().setVisible(true);
    }

    /**
     * Sends a login request to the server.
     *
     * @param username the username
     * @param password the password
     */
    public static void login(String username, String password) {
        System.out.println("Logging in with username: " + username + " and password: " + password);
    }

    /**
     * Sends a register request to the server.
     *
     * @param username the username
     * @param password the password
     */
    public static void register(String username, String password) {
        System.out.println("Registering with username: " + username + " and password: " + password);
    }



}