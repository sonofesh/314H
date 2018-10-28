package assignment;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Boggle {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Type in \"START\" and hit enter if you want to begin a new game");
        String c = input.nextLine().toLowerCase();
        if (c.equals("start")) {
            runGame();
        }
//        GameManager game = new GameManager();
//        BoggleDictionary dict = new GameDictionary();
//        dict.loadDictionary("words.txt");
//        game.newGame(4, 1, "cubes.txt", dict);
//        char[][] board = new char[][]{
//                {'e','e','c','a'},
//                {'a','l','e','p'},
//                {'h','n', 'b', 'o'},
//                {'q','t','t','y'}};
//        displayBoard(game);
//        game.addWord("alec", 0);
//        displayWord(game);

    }

    public static void runGame() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("How many players are there?");
        int playerCount = input.nextInt();
        input.nextLine();
        System.out.println("Please enter in a valid text file path otherwise the default dictionary will be used");
        String dictFile = input.nextLine();
        BoggleDictionary dict = new GameDictionary();
        try {
            dict.loadDictionary(dictFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            dict.loadDictionary("words.txt");
        }
        System.out.println("Please enter in a valid text file path otherwise the default cube file will be used");
        String cubeFile = input.nextLine();
        BoggleDictionary cube = new GameDictionary();
        int size = 4;
        try {
            Scanner test = new Scanner(new File(cubeFile));
            test.next();
            System.out.println("What size board would you like?");
            size = input.nextInt();
            input.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }

        GameManager game = new GameManager();
        boolean[] playerDone = new boolean[playerCount];
        game.newGame(size, playerCount, "cubes.txt", dict);

        while (!isDone(playerDone)) {
            for (int i = 0; i < playerCount; i++) {
                if (!playerDone[i]) {
                    System.out.print("\n");
                    printScore(game);
                    System.out.print("\n");
                    displayBoard(game);
                    System.out.print("\n");
                    System.out.println("Enter in a word player " + i);
                    String word = input.next();
                    int val = game.addWord(word, i);
                    while (val == 0) {
                        System.out.println("Word is not valid try again. If you want to give up type in DONE!");
                        word = input.next();
                        if (word.equals("DONE!")) {
                            playerDone[i] = true;
                            break;
                        }
                        val = game.addWord(word, i);
                    }
                    if (val != 0) {
                        displayWord(game);
                    }
                }
            }
        }
        HashSet<String> words = (HashSet<String>) game.getAllWords();
        int score = 0;
        System.out.print("MISSED WORDS: ");
        for (String word: words) {
            System.out.print(word + " ");
            score += word.length() - 3;
        }
        System.out.println("Computer Score: " + score);

        System.out.println("Type in \"START\" and hit enter if you want to begin another game");
        String c = input.nextLine().toLowerCase();
        if (c.equals("start")) {
            runGame();
        }
    }

    private static boolean isDone (boolean[] done) {
        for (boolean b: done) {
            if (!b) return false;
        }
        return true;
    }

    private static void printScore (GameManager game) {
        int[] scores = game.playerScores;
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Player " + i + " Score: " + scores[i]);
        }
    }

    private static void displayBoard(GameManager game) {
        char[][] board = game.getBoard();
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void displayWord(GameManager game) {
        char[][] board = game.getBoard();
        List<Point> points = game.getLastAddedWord();
        if (points == null)
            return;
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boolean upper = false;
                for (Point point: points) {
                    if (i == point.y && j == point.x) {
                        System.out.print((char) (board[i][j] - 32) + " ");
                        upper = true;
                    }
                }
                if (upper != true)
                    System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }



}
