package assignment;

import org.junit.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static junit.framework.Assert.*;

public class TestDictionary {
    //loadDictorary Tests

    /**
     * Tests whether words are loaded correctly by seeing that the dictionary
     */
    @Test
    public void testLoadDictionary () {
        GameDictionary dict = new GameDictionary();
        try {
            dict.loadDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String file = "";
        try {
            Scanner c = new Scanner (new File("words.txt"));
            while (c.hasNext()) {
                file += c.nextLine();
            }
            String dictionaryResults = "";

            assertEquals(dict.getWords(), file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //tests wheter the tree was properly constructed
    @Test
    public void testTreeConstruction () {
        
    }

    @Test
    public void testIsPrefix () {

    }

    @Test
    public void testContains () {

    }

    /**
     * Tests large and peculiar input sizes
     */
    @Test
    public void testStrangeInputs () {

    }
}
