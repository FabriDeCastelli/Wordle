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

    public static volatile String currentWord;
    private static final List<String> dictionary;
    private static final List<String> extractedWords;

    static {
        extractedWords = new ArrayList<>();
        dictionary = new ArrayList<>();
        try (final RandomAccessFile file =
                     new RandomAccessFile("src/main/java/server/conf/words.txt", "r")) {
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
     * Runs the service.
     */
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
        final String word = dictionary.get(random.nextInt(notExtractedWords.size()));
        extractedWords.add(word);
        currentWord = word;

    }


}
