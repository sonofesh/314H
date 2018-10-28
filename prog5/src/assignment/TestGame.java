package assignment;


import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestGame {

    @Test
    public void testDictionarySearch() {
        GameManager game = new GameManager();

        //set up dictionary
        GameDictionary dict = new GameDictionary();
        try {
            dict.loadDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            game.newGame(4, 2, "cubes.txt", dict);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dictionarySearch = "";
        game.setSearchTactic(BoggleGame.SearchTactic.SEARCH_DICT);
        Collection<String> words = game.getAllWords();
        for (String word: words) {
            System.out.print(word + " ");
            dictionarySearch += word;
        }

        System.out.println("\n\n");

        String boardSearch = "";
        game.setSearchTactic(BoggleGame.SearchTactic.SEARCH_BOARD);
        words = game.getAllWords();
        for (String word: words) {
            System.out.print(word + " ");
            boardSearch += word;
        }

        assertEquals(dictionarySearch, boardSearch);

    }

}
