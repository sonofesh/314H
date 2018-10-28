package assignment;

import java.awt.Point;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class GameManager implements BoggleGame {
    char[][] board;
    int[] playerScores;
    List<Point> lastAddedWord;
    String lastWord;
    BoggleDictionary dict;
    SearchTactic tactic;
    ArrayList<String> playerWords;
    HashSet<String> words = new HashSet<String>();

    @Override
    public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
        board = new char[size][size];
        playerScores = new int[numPlayers];
        //add each player to TreeMap
        playerWords = new ArrayList<>();

        this.dict = dict;
        tactic = BoggleGame.SEARCH_DEFAULT;
        try {
            buildBoard(cubeFile);
        } catch (FileNotFoundException e) {
            System.err.print("File not found");
        }
    }

    /**
     * Helper method takes cubeFile and creates the cubes from it. From there, it shuffles them around
     * and places each one in a new position in the board
     * @param cubeFile
     */
    public void buildBoard (String cubeFile) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(cubeFile));
        ArrayList<String> cubes = new ArrayList<String>();
        for(int i = 0; i < board.length * board.length; i++) {
            cubes.add(sc.nextLine());
        }
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                int cubeIndex = (int)(Math.random() * cubes.size());
                String cube = cubes.get(cubeIndex);
                char c = cube.charAt((int)(Math.random() * 6));
                board[i][j] = Character.toLowerCase(c);
                cubes.remove(cubeIndex);
            }
        }
    }

    @Override
    public char[][] getBoard() {
        // TODO Auto-generated method stub
        return board;
    }


    @Override
    public int addWord(String word, int player) {
        boolean isWord = false;
        getAllWords();
        //checks whether word is on board and in the list of words the player already added
        if (!words.contains(word) || playerWords.contains(word)) {
            return 0;
        }
        int point = word.length() - 3;
        lastWord = word;
        playerScores[player] += point;
        playerWords.add(word);
        List<Point> points = new ArrayList<>();
        //traces the path of the word
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(word.charAt(0) == board[i][j]) {
                    //System.out.println("new");
                    boolean[][] visited = new boolean[board.length][board.length];
                    wordTrace(word,0, i, j, visited, new ArrayList<>());
                    if (lastAddedWord != null) {
                        return point;
                    }
                }
            }
        }
        return point;
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[i].length; j++) {
//                if (word.charAt(0) == board[i][j]) {
//                    boolean[][] visited = new boolean[board.length][board.length];
//                    if (wordOnBoard(i, j, "", visited, word) == true) {
//                        isWord = true;
//                        break;
//                    }
//                }
//            }
//        }
    }

    private void wordTrace(String word, int index, int i, int j, boolean[][] visited, List<Point> points) {
        if(word.charAt(index) == board[i][j]) {
            points.add(new Point(j, i));
            if(word.charAt(word.length() - 1) == board[i][j]) {
                //System.out.println("END" + i + " " + j);
                lastAddedWord = points;
                return;
            }
            visited[i][j] = true;
            boolean found = false;
            for(int k = i-1; k < i+2; k++) {
                for(int l = j -1; l < j+2; l++) {
                    if(k>= 0 && k < board.length && l>= 0 && l < board.length && visited[k][l] == false) {
                        //System.out.println(j + " " + i);
                        wordTrace(word,index+1, k, l, visited, points);
                    }
                }
            }
            visited[i][j] = false;
        }
//        else {
//            if (points != null && points.size() != 0)
//                points.remove(points.size() - 1);
////            if (index > 0)
////                wordTrace(word,index-1, i, j, visited, points);
////            else
////                return;
//        }
    }

    @Override
    public List<Point> getLastAddedWord() {
        if (lastWord == null)
            return null;
        List<Point> last = new ArrayList<>();
        HashMap<Character, Point> chars = new HashMap<>();
        for (Point p: lastAddedWord) {
            if (board[p.y][p.x] == lastWord.charAt(lastWord.length() - 1)) {
                last.add(p);
                return last;
            }
            if (chars.containsKey(board[p.y][p.x])) {
                last.remove(chars.get(board[p.y][p.x]));
            }
            chars.put(board[p.y][p.x], p);
            last.add(p);
        }
        return last;
    }

    @Override
    public void setGame(char[][] board) {
        this.board = new char[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                this.board[i][j] = board[i][j];
            }
        }
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
        playerWords.clear();
        lastAddedWord = new ArrayList<>();
        lastWord = null;
    }

    @Override
    public Collection<String> getAllWords() {
        // TODO Auto-generated method stub
        words.clear();
        if(this.tactic == BoggleGame.SearchTactic.SEARCH_BOARD) {
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[i].length; j++) {
                    boolean[][] visited = new boolean[board.length][board.length];
                    boardDrivenSearch(i, j, "", visited);
                }
            }
        }
//        else {
//            boolean[][] visited = new boolean[board.length][board[0].length];
//            Iterator<String> iter = dict.iterator();
//            String word = iter.next();
//            dictionaryDrivenSearch(0, 0, "", word, visited, iter);
//
//        }
        else {
            Iterator<String> dictionary = dict.iterator();
            while(dictionary.hasNext()) {
                String word = dictionary.next();
                int index = 0;
                for(int i = 0; i < board.length; i++) {
                    for(int j = 0; j < board[0].length; j++) {
                        if(word.charAt(index) == board[i][j]) {
                            boolean[][] visited = new boolean[board.length][board.length];
                            searchBoard(word,index, i, j, visited);
                        }
                    }
                }
            }
        }
        return words;
    }

    private void searchBoard(String word, int index, int i, int j, boolean[][] visited) {
        if(word.charAt(index) == board[i][j]) {
            if(index+1==word.length()) {
                if(word.length()>3 && !playerWords.contains(word)) {
                    words.add(word);
                }
                return;
            }
            visited[i][j] = true;
            for(int k = i-1; k < i+2; k++) {
                for(int l = j -1; l < j+2; l++) {
                    if(k>= 0 && k < board.length && l>= 0 && l < board.length && visited[k][l] == false) {
                        searchBoard(word,index+1, k, l, visited);
                    }
                }
            }
            visited[i][j] = false;
        }
        else {
            return;
        }
    }



    /**
     * Method utilizes a depth first search algorithm
     * @param i
     * @param j
     * @param currentString
     * @param visited
     */
    private void boardDrivenSearch (int i, int j, String currentString, boolean[][] visited) {
        currentString += board[i][j];
        if(!dict.isPrefix(currentString)) {
            return;
        }
        else {
            visited[i][j] = true;
            if(dict.contains(currentString) && currentString.length() > 3 && !playerWords.contains(currentString)) {
                words.add(currentString);
            }
            //accesses all the pieces surrounding the current one on the board
            for(int k = i-1; k < i+2; k++) { //wouldn't work for variable board sizes so adjust
                for(int l = j-1; l < j+2; l++) {
                    if(k >= 0 && k < board.length && l >= 0 && l < board.length && visited[k][l] == false) {
                        boardDrivenSearch(k, l, currentString, visited);
                    }
                }
            }
            visited[i][j] = false;
        }
    }


    private void dictionaryDrivenSearch (int i, int j, String currentString, String word, boolean[][] visited, Iterator<String> iter) {
        if (currentString.length() == 0) {
            currentString += word.substring(0, 1);
            ArrayList<Integer> indices = inBoard((currentString.charAt(0)));

            for (int in = 0; in < indices.size(); in += 2) {
                i = indices.get(in);
                j = indices.get(in + 1);
                dictionaryDrivenSearch(i, j, currentString, word, visited, iter);
            }

            if (indices.isEmpty()) {
                visited = new boolean[board.length][board[0].length]; //edit later won't work for irregular arrays
                while (inBoard(word.charAt(0)).isEmpty()) {
                    if (iter.hasNext())
                        word = iter.next();
                    else
                        return;
                }
                currentString = "";
                dictionaryDrivenSearch(i, j, currentString, word, visited, iter);
            }
        }

        else if (currentString.length() < word.length()) {
            boolean found = false;
            visited[i][j] = true;
            //accesses all the pieces surrounding the current one on the board
            for(int k = i-1; k < i+2; k++) { //wouldn't work for variable board sizes so adjust
                for(int l = j-1; l < j+2; l++) {
                    if (k >= 0 && k < board.length && l >= 0 && l < board.length && visited[k][l] == false) {
                        int length = currentString.length();
                        if (board[k][l] == word.charAt(length)) {
                            found = true;
                            visited[k][l] = true;
                            currentString += word.substring(length, length + 1);
                            dictionaryDrivenSearch(k, l, currentString, word, visited, iter);
                        }
                    }
//                    else if (k >= 0 && k < board.length && l >= 0 && l < board.length && visited[k][l] == false) {
////                        currentString = "";
////                        //insection of current word and next word
//                        dictionaryDrivenSearch(k, l, currentString.substring(0, currentString.length() - 1), word, visited, iter);
//                    }
                }
            }
            //if nothing is found then you know current path won't lead anywhere
            if (!found) {
                int length = currentString.length();
                String wordNotFound = word.substring(length, length + 1);
                while (commonStart(wordNotFound, word).equals(wordNotFound)) {
                    if (iter.hasNext())
                        word = iter.next();
                    else
                        return;
                }                String common = commonStart(currentString, word);
                dictionaryDrivenSearch(i, j, common, word, visited, iter);
//                if (common.length() == 0)
//                    dictionaryDrivenSearch(i, j, "", word, new boolean[board.length][board[0].length], iter);
//                else
//                    dictionaryDrivenSearch(i, j, common, word, visited, iter);
            }

        }

        else if (dict.contains(currentString)) {
            if (currentString.length() > 3)
                words.add(currentString);
            System.out.println(currentString);
            if (iter.hasNext())
                word = iter.next();
            else
                return;
            String common = commonStart(currentString, word);
            if (common.length() == 0)
                dictionaryDrivenSearch(i, j, "", word, new boolean[board.length][board[0].length], iter);
            else
                dictionaryDrivenSearch(i, j, common, word, visited, iter);
        }


    }

    /**
     * Helper method that searchs through array for param c and returns character's index if found.
     * Otherwise, it returns an empty arraylist
     * @param c
     * @return
     */
    private ArrayList inBoard (char c) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == c) {
                    indices.add(i);
                    indices.add(j);
                }
            }
        }
        return indices;
    }

    /**
     * Helper method that returns the section that two Strings share
     * @return
     */
    private String commonStart (String a, String b) {
        String result = "";
        for (int i = 0; i < a.length() && i < b.length(); i++) {
            if (a.charAt(i) == b.charAt(i))
                result += a.substring(i, i + 1);
            else
                break;
        }
        return result;
    }


    public void printWords() {
        for (String word: words)
            System.out.println(word);
    }

    @Override
    public void setSearchTactic(SearchTactic tactic) {
        // TODO Auto-generated method stub
        this.tactic = tactic;
    }

    @Override
    public int[] getScores() {
        return playerScores;
    }

//    public static void main(String[] args) throws IOException {
//        GameManager game = new GameManager();
//        BoggleDictionary dict = new GameDictionary();
//        dict.loadDictionary("words.txt");
//        game.newGame(4, 1, "cubes.txt", dict);
//        game.setSearchTactic(SearchTactic.SEARCH_DICT);
//        game.getAllWords();
//        game.printWords();
//        System.out.println(game.addWord("alec", 0));
//        System.out.println(game.getLastAddedWord());
//    }
}

