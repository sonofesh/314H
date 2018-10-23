package assignment;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Dictionary stores words that can be used in the game such that they can be efficiently (runtime less than logN) retrieved
 * The implementation of this data structure is similair to a tree except that each child can have up to 26 nodes rather than
 * just 2.
 */
public class GameDictionary implements BoggleDictionary {
    CharacterNode head;
    ArrayList<String> words = new ArrayList<String>();

    public GameDictionary() {
        head = new CharacterNode('!');
    }

    @Override
    public void loadDictionary(String filename) throws IOException {
        Scanner sc = new Scanner(new File(filename));
        CharacterNode current;

        while(sc.hasNextLine()){
            current = head;
            String line = sc.nextLine();
            words.add(line);
            for(int i = 0; i < line.length(); i++){
                current = current.setNode(line.charAt(i));
            }
            //
            current.setNode('{');
        }
    }

    @Override
    public boolean isPrefix(String prefix) {
        CharacterNode current = head;

        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(current.getNode(c) == null){
                return false;
            }
            current = current.getNode(c);
        }

        return true;
    }

    @Override
    public boolean contains(String word) {
        CharacterNode current = head;

        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if(current.getNode(c) == null){
                return false;
            }
            current = current.getNode(c);
            if (i == word.length() - 1) {
                if (current.getNode('{') == null){
                    return false;
                }
            }
        }

        return true;
    }

    //temp method used for testing purposes
    public String getWords() {
        String result = "";
        for (String word: words) {
            result += word;
        }

        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return words.iterator();

    }

    private class CharacterNode {
        char val;
        //each node has an array of 26 nodes (letters in alphabet) it points to
        CharacterNode[] nodes;

        CharacterNode(char c){
            val = c;
            //nodes store the 26 possible letters that could connect to the current node and also an arbitary character
            //in the 27th index that denotes the end of a word
            nodes = new CharacterNode[27];
        }

        /**
         * Used to set add a node to the current node
         * @param c
         */
        private CharacterNode setNode(char c) {
            //since dictionary provides lower case words we can determine the index we should
            //place a word by subtracting the value of c by the ASCII value of 'a'
            if (nodes[c-97] == null)
                nodes[c-97] = new CharacterNode(c);
            return nodes[c-97];
        }

        private CharacterNode getNode(char c){
            return nodes[c-97];
        }
    }

}
