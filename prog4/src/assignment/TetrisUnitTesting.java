package assignment;
import org.junit.*;

import java.awt.*;

import static junit.framework.Assert.*;

public class TetrisUnitTesting {
    @Test
    public void test () {
        String[] bodyString = new String[4];
        bodyString[0] = "010111000";
        bodyString[1] = "010011010";
        bodyString[2] = "000111010";
        bodyString[3] = "010110010";

        String[] skirtString = new String[4];
        skirtString[0] = "111";
        skirtString[1] = "X01";
        skirtString[2] = "101";
        skirtString[3] = "10X";

        assertTrue(testPiece(Piece.PieceType.T, bodyString, skirtString));
    }

    @Test
    public void testPieceSQUARE() {
        String[] bodyString = new String[4];
        bodyString[0] = "1111";
        bodyString[1] = "1111";
        bodyString[2] = "1111";
        bodyString[3] = "1111";

        String[] skirtString = new String[4];
        skirtString[0] = "00";
        skirtString[1] = "00";
        skirtString[2] = "00";
        skirtString[3] = "00";

        assertTrue(testPiece(Piece.PieceType.SQUARE, bodyString, skirtString));
    }

    @Test
    public void testPieceSTICK() {
        String[] bodyString = new String[4];
        bodyString[0] = "0000111100000000";
        bodyString[1] = "0010001000100010";
        bodyString[2] = "0000000011110000";
        bodyString[3] = "0100010001000100";

        String[] skirtString = new String[4];
        skirtString[0] = "2222";
        skirtString[1] = "XX0X";
        skirtString[2] = "1111";
        skirtString[3] = "X0XX";

        assertTrue(testPiece(Piece.PieceType.STICK, bodyString, skirtString));
    }


    @Test
    public void testPieceLEFT_L() {
        String[] bodyString = new String[4];
        bodyString[0] = "100111000";
        bodyString[1] = "011010010";
        bodyString[2] = "000111001";
        bodyString[3] = "010010110";

        String[] skirtString = new String[4];
        skirtString[0] = "111";
        skirtString[1] = "X02";
        skirtString[2] = "110";
        skirtString[3] = "00X";

        assertTrue(testPiece(Piece.PieceType.LEFT_L, bodyString, skirtString));
    }

    @Test
    public void testPieceRIGHT_L() {
        String[] bodyString = new String[4];
        bodyString[0] = "001111000";
        bodyString[1] = "010010011";
        bodyString[2] = "000111100";
        bodyString[3] = "110010010";

        String[] skirtString = new String[4];
        skirtString[0] = "111";
        skirtString[1] = "X00";
        skirtString[2] = "011";
        skirtString[3] = "20X";

        assertTrue(testPiece(Piece.PieceType.RIGHT_L, bodyString, skirtString));
    }

    @Test
    public void testPieceLEFT_DOG() {
        String[] bodyString = new String[4];
        bodyString[0] = "110011000";
        bodyString[1] = "001011010";
        bodyString[2] = "000110011";
        bodyString[3] = "010110100";

        String[] skirtString = new String[4];
        skirtString[0] = "211";
        skirtString[1] = "X01";
        skirtString[2] = "100";
        skirtString[3] = "01X";

        assertTrue(testPiece(Piece.PieceType.LEFT_DOG, bodyString, skirtString));
    }

    @Test
    public void testPieceRIGHT_DOG() {
        String[] bodyString = new String[4];
        bodyString[0] = "011110000";
        bodyString[1] = "010011001";
        bodyString[2] = "000011110";
        bodyString[3] = "100110010";

        String[] skirtString = new String[4];
        skirtString[0] = "112";
        skirtString[1] = "X10";
        skirtString[2] = "001";
        skirtString[3] = "10X";

        assertTrue(testPiece(Piece.PieceType.RIGHT_DOG, bodyString, skirtString));
    }

    @Test
    public void testBoard() {
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        assertTrue(compareBoards(board, boardString));
    }



    @Test
    public void TestTPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.T), new Point(4, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.T).counterclockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.T).clockwisePiece(), new Point(2, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.T).clockwisePiece().clockwisePiece(), new Point(3, 20));
        board.move(Board.Action.DROP);
        boardString[20] = "0001111000";
        boardString[21] = "0001111000";
        boardString[22] = "0001111000";
        boardString[23] = "0001111000";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestSQUAREPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.SQUARE), new Point(0, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.SQUARE).counterclockwisePiece(), new Point(2, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.SQUARE).clockwisePiece(), new Point(4, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.SQUARE).clockwisePiece().clockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);
        boardString[20] = "0000011000";
        boardString[21] = "0000011000";
        boardString[22] = "1111110000";
        boardString[23] = "1111110000";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestSTICKPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.STICK).clockwisePiece(), new Point(4, 20));
        for(int i = 0; i < 10; i++) {
            board.move(Board.Action.LEFT);
        }
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.STICK), new Point(2, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.STICK).clockwisePiece().clockwisePiece(), new Point(4, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.STICK).clockwisePiece(), new Point(5, 20));
        for(int i = 0; i < 10; i++){
            board.move(Board.Action.RIGHT);
        }
        board.move(Board.Action.DROP);
        boardString[20] = "1000000001";
        boardString[21] = "1000000001";
        boardString[22] = "1000111101";
        boardString[23] = "1011110001";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestLEFT_LPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.LEFT_L), new Point(4, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.LEFT_L).clockwisePiece().clockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);

        boardString[20] = "0000000000";
        boardString[21] = "0000000000";
        boardString[22] = "0000111100";
        boardString[23] = "0000111100";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestRIGHT_LPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.RIGHT_L), new Point(6, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.RIGHT_L).clockwisePiece().clockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);

        boardString[20] = "0000000000";
        boardString[21] = "0000000000";
        boardString[22] = "0000011110";
        boardString[23] = "0000011110";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestLEFT_DOGPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.LEFT_DOG), new Point(4, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.LEFT_DOG).clockwisePiece().clockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);

        boardString[20] = "0000000000";
        boardString[21] = "0000011000";
        boardString[22] = "0000111100";
        boardString[23] = "0000011000";
        assertTrue(compareBoards(board, boardString));
    }

    @Test
    public void TestRIGHT_DOGPlacement(){
        Board board = new TetrisBoard(10, 24);
        String[] boardString = new String[24];
        for(int i = 0; i < boardString.length; i++){
            boardString[i] = "0000000000";
        }
        board.nextPiece(new TetrisPiece(Piece.PieceType.RIGHT_DOG), new Point(6, 20));
        board.move(Board.Action.DROP);
        board.nextPiece(new TetrisPiece(Piece.PieceType.RIGHT_DOG).clockwisePiece().clockwisePiece(), new Point(5, 20));
        board.move(Board.Action.DROP);

        boardString[20] = "0000000000";
        boardString[21] = "0000001100";
        boardString[22] = "0000011110";
        boardString[23] = "0000001100";
        assertTrue(compareBoards(board, boardString));
    }

    private boolean compareBoards(Board board, String boardString){
        Piece.PieceType[][] grid = initializeGrid(boardString);
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y] == null){
                    if(board.getGrid(x, y) != null){
                        return false;
                    }
                } else if(grid[x][y] != null && board.getGrid(x, y) == null){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compareBoards(Board board, String[] boardString){
        Piece.PieceType[][] grid = initializeGrid(boardString);
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y] == null){
                    if(board.getGrid(x, y) != null){
                        return false;
                    }
                } else if(grid[x][y] != null && board.getGrid(x, y) == null){
                    return false;
                }
            }
        }
        return true;
    }

    private Piece.PieceType[][] initializeGrid(String boardString){
        int lines = 1;
        for(int i = 0; i < boardString.length(); i++){
            if(boardString.charAt(i) == '\n') {
                lines++;
            }
        }
        String[] strings = new String[lines];
        for(int i = 0; i < lines; i++){
            strings[i] = "";
        }
        int size = 0;
        for(int i = 0; i < boardString.length(); i++){
            if(boardString.charAt(i) == '\n'){
                size++;
                continue;
            }
            strings[size] += boardString.charAt(i);
        }
        return initializeGrid(strings);
    }

    private Piece.PieceType[][] initializeGrid(String[] input){
        Piece.PieceType[][] grid = new Piece.PieceType[input[0].length()][input.length];
        for(int y = grid[0].length - 1; y >= 0; y--){
            for(int x = 0; x < grid.length; x++){
                if(input[input.length - y - 1].charAt(x) != '0'){
                    grid[x][y] = Piece.PieceType.STICK;
                } else {
                    grid[x][y] = null;
                }
            }
        }
        return grid;
    }


    private boolean testPiece(Piece.PieceType type, String[] bodyString, String[] skirtString){
        for(int rotation = 0; rotation < 4; rotation++){
            if(!testPiece(type, rotation, bodyString[rotation], skirtString[rotation])){
                return false;
            }
        }
        return true;
    }

    private boolean testPiece(Piece.PieceType type, int rotation, String bodyString, String skirtString){
        Piece piece = new TetrisPiece(type);
        while(rotation > 0) {
            piece = piece.clockwisePiece();
            rotation--;
        }
        Point[] body = initializeBody(bodyString);
        int[] skirt = initializeSkirt(skirtString);
        if(!compareBodies(piece.getBody(), body)){
            return false;
        }
        if(!compareSkirts(piece.getSkirt(), skirt)){
            return false;
        }
        return true;
    }


    private boolean compareBodies(Point[] lhs, Point[] rhs){
        if(lhs.length != rhs.length){
            return false;
        }
        for(int i = 0; i < lhs.length; i++){
            boolean isEqual = false;
            for(int j = 0; j < rhs.length; j++){
                if(lhs[i].equals(rhs[j])){
                    isEqual = true;
                    break;
                }
            }
            if(!isEqual){
                return false;
            }
        }
        return true;
    }

    private Point[] initializeBody(String input){
        int count = 0;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '1'){
                count++;
            }
        }
        int width = (int) Math.sqrt(input.length());
        Point[] output = new Point[count];

        int size = 0;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '1'){
                int x = i;
                while(x >= width){
                    x -= width;
                }
                int y = width - (i / width) - 1;
                output[size] = new Point(x, y);
                size++;
            }
        }
        return output;
    }

    private int[] initializeSkirt(String input){
        int[] skirt = new int[input.length()];
        for(int i = 0; i < skirt.length; i++){
            if(input.substring(i, i + 1).equals("X")){
                skirt[i] = Integer.MAX_VALUE;
            } else {
                skirt[i] = Integer.parseInt(input.substring(i, i + 1));
            }
        }
        return skirt;
    }

    private boolean compareSkirts(int[] lhs, int[] rhs){
        if(lhs.length != rhs.length){
            return false;
        }
        for(int i = 0; i < lhs.length; i++){
            if(lhs[i] != rhs[i]){
                return false;
            }
        }
        return true;
    }
}
