package se.kth.id1212.server.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2017-11-16.
 */
public class DataHandler {
    private static final String DICTIONARY = "words.txt";
    private List<String> words = new ArrayList<>();

    public DataHandler() {
        try {
            readFile();
        }
        catch (Exception e) {

            System.out.println(e);
        }
    }

    public String retrieveWord() {
        return words.get((int)(Math.random()*words.size()));
    }

    private void readFile() throws IOException {
        try (BufferedReader fromFile = new BufferedReader(new FileReader(DICTIONARY))) {
            fromFile.lines().forEachOrdered(line -> words.add(line.toUpperCase()));
        }
    }
}
