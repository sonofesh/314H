package assignment;

import org.junit.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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

    //tests whether the tree was properly constructed
    @Test
    public void testTreeConstruction () {
        
    }

    @Test
    public void testIsPrefix () {

    }

    @Test
    public void testContains () {
        GameDictionary dict = new GameDictionary();
        try {
            dict.loadDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(dict.contains("ab"));
        assertTrue(dict.contains("abaci"));
    }

    @Test
    public void testIterator() {
        String fromFile = "";
        String fromItr = "";

        GameDictionary d = new GameDictionary();
        Iterator<String> iter = d.iterator();

        try {
            d.loadDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner c = null;
        try {
            c = new Scanner(new File("words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (c.hasNext()) {
            fromFile += c.nextLine();
        }

        for (String word: d) {
            fromItr += word;
        }

        assertEquals(fromFile, fromItr);

        fromItr = "";

        while (iter.hasNext()) {
            fromItr += (String) iter.next();
        }

        assertEquals(fromFile, fromItr);
    }

    /**
     * Tests large and peculiar input sizes
     */
    @Test
    public void testStrangeInputs () {

    }
}
