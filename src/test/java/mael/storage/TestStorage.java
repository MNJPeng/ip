package mael.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestStorage {
    
    @Test
    public void load_stubDataFile_loadedCorrectly() {
        Storage storage = new Storage("data/testData.txt");
        FileWriter writer;
        String task1 = "T | X | xxx";
        String task2 = "D |   | yy | 12121212 1212";
        String task3 = "E |   | z | 12121212 1212 | 12121312 1212";
        try {
            writer = new FileWriter(new File("./data/testData.txt"));
            writer.write(task1 + "\n" + task2 + "\n" + task3);
            writer.close();
        } catch (IOException e) {
            assert(false);
        }
        ArrayList<String> tasks = storage.load();
        assertTrue(tasks.get(0).equals(task1));
        assertTrue(tasks.get(1).equals(task2));
        assertTrue(tasks.get(2).equals(task3));
    }
    
    @Test
    public void load_missingDataFileAndFolders_loadedEmpty() {
        File folder = new File("./data/just");
        folder.delete();
        Storage storage = new Storage("data/just/for/fun.txt");
        assertTrue(storage.load().isEmpty());
    }

    @Test
    public void save_() {

    }
}
