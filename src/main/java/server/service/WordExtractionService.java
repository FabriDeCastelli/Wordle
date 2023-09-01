package server.service;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Word extraction service.
 * Periodically extract a new word from the dictionary.
 */
public class WordExtractionService extends Thread {

    private static volatile String currentWord;
    private static final List<String> dictionary;
    private static final List<String> extractedWords;

    /*
     * Static block to initialize the dictionary, as instance-independent.
     */
    static {
        extractedWords = new ArrayList<>();
        dictionary = new ArrayList<>();
        try (final RandomAccessFile file =
                     new RandomAccessFile("src/main/java/server/config/words.txt", "r")) {
            String line = file.readLine();
            while (line != null) {
                dictionary.add(line);
                line = file.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error reading words from file.");
        }
    }


    /**
     * Constructor for WordExtractionService.
     */
    public WordExtractionService() {
    }

    /**
     * Gets the current word.
     *
     * @return the current word
     */
    public static String getCurrentWord() {
        return currentWord;
    }

    /**
     * Gets the list of already extracted words.
     *
     * @return the extracted words list
     */
    public static List<String> getExtractedWords() {
        return extractedWords;
    }


    @Override
    public void run() {
        this.extractWord();
    }


    /**
     * Randomly extracts a new word from the dictionary.
     */
    private void extractWord() {

        final Random random = new Random();
        final List<String> notExtractedWords =
                dictionary.stream().filter(word -> !extractedWords.contains(word)).toList();
        final String word = notExtractedWords.get(random.nextInt(notExtractedWords.size()));
        extractedWords.add(word);
        currentWord = word;
        System.out.println("Extracted new word: " + word);

    }

}