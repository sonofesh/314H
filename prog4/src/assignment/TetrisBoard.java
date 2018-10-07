package assignment;

import java.awt.*;

/**
 * Represents a Tetris board -- essentially a 2-d grid of piece types (or nulls). Supports
 * tetris pieces and row clearing.  Does not do any drawing or have any idea of
 * pixels. Instead, just represents the abstract 2-d board.
 */
public final class TetrisBoard implements Board {
    private Piece.PieceType[][] grid;
    private Piece currentPiece;
    private Point currentPiecePosition;
    private Result lastResult;
    private Action lastAction;

    private int rowsCleared = 0;
    private int maxHeight = 0;
    private int[] columnHeight;
    private int[] rowWidth;

    // JTetris will use this constructor
    public TetrisBoard(int width, int height) {
        grid = new Piece.PieceType[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                grid[x][y] = null;
            }
        }
        initializeArrays();
    }

    private TetrisBoard(TetrisBoard input){
        grid = new Piece.PieceType[input.getWidth()][input.getHeight()];
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                grid[x][y] = input.getGrid(x, y);
            }
        }
        currentPiece = input.getCurrentPiece();
        currentPiecePosition = new Point((int) input.getCurrentPiecePosition().getX(),
                (int) input.getCurrentPiecePosition().getY());
        lastResult = input.getLastResult();
        lastAction = input.getLastAction();
        rowsCleared = input.getRowsCleared();
        maxHeight = input.getMaxHeight();
        columnHeight = new int[getWidth()];
        for(int x = 0; x < columnHeight.length; x++){
            columnHeight[x] = input.getColumnHeight(x);
        }
        rowWidth = new int[getHeight()];
        for(int y = 0; y < rowWidth.length; y++){
            rowWidth[y] = input.getRowWidth(y);
        }
    }

    private void initializeArrays(){
        columnHeight = new int[getWidth()];
        rowWidth = new int[getHeight()];
        for(int i = 0; i < getWidth(); i++){
            columnHeight[i] = 0;
        }
        for(int i = 0; i < getHeight(); i++){
            rowWidth[i] = 0;
        }
    }

    @Override
    public Result move(Action act) {
        lastAction = act;
        if(currentPiece == null){
            lastResult = Result.NO_PIECE;
            return lastResult;
        }
        switch(act) {
            case LEFT:
                currentPiecePosition.setLocation(currentPiecePosition.getX() - 1,
                        currentPiecePosition.getY());
                try {
                    nextPiece(currentPiece, currentPiecePosition);
                } catch(IllegalArgumentException ex){
                    currentPiecePosition.setLocation(currentPiecePosition.getX() + 1,
                            currentPiecePosition.getY());
                    lastResult = Result.OUT_BOUNDS;
                    return lastResult;
                }
                break;
            case RIGHT:
                currentPiecePosition.setLocation(currentPiecePosition.getX() + 1,
                        currentPiecePosition.getY());
                try {
                    nextPiece(currentPiece, currentPiecePosition);
                } catch(IllegalArgumentException ex){
                    currentPiecePosition.setLocation(currentPiecePosition.getX() - 1,
                            currentPiecePosition.getY());
                    lastResult = Result.OUT_BOUNDS;
                    return lastResult;
                }
                break;
            case DOWN:
                if(currentPiecePosition.getY() > dropHeight(currentPiece, (int) currentPiecePosition.getX())){
                    currentPiecePosition.setLocation(currentPiecePosition.getX(),
                            currentPiecePosition.getY() - 1);
                    try {
                        nextPiece(currentPiece, currentPiecePosition);
                    } catch(IllegalArgumentException ex){
                        currentPiecePosition.setLocation(currentPiecePosition.getX(),
                                currentPiecePosition.getY() + 1);
                        lastResult = Result.OUT_BOUNDS;
                        return lastResult;
                    }
                    if(currentPiecePosition.getY() != dropHeight(currentPiece, (int) currentPiecePosition.getX())){
                        break;
                    }
                }
            case DROP:
                currentPiecePosition.setLocation(currentPiecePosition.getX(),
                        dropHeight(currentPiece, (int) currentPiecePosition.getX()));
                placePiece();
                lastResult = Result.PLACE;
                return lastResult;
            case CLOCKWISE: {
                lastResult = Result.OUT_BOUNDS;
                Point nextPiecePosition;
                Point[] kicks;
                if (currentPiece.getType() == Piece.PieceType.STICK) {
                    kicks = Piece.I_CLOCKWISE_WALL_KICKS[currentPiece.getRotationIndex()];
                } else {
                    kicks = Piece.NORMAL_CLOCKWISE_WALL_KICKS[currentPiece.getRotationIndex()];
                }
                for (int i = 0; i < kicks.length; i++) {
                    nextPiecePosition = new Point((int) (currentPiecePosition.getX() + kicks[i].getX()),
                            (int) (currentPiecePosition.getY() + kicks[i].getY()));
                    try {
                        nextPiece(currentPiece.clockwisePiece(), nextPiecePosition);
                    } catch (IllegalArgumentException ex) {
                        continue;
                    }
                    lastResult = Result.SUCCESS;
                    break;
                }
                if (lastResult != Result.SUCCESS) {
                    return lastResult;
                }
                break;
            } case COUNTERCLOCKWISE: {
                lastResult = Result.OUT_BOUNDS;
                Point nextPiecePosition;
                Point[] kicks;
                if (currentPiece.getType() == Piece.PieceType.STICK) {
                    kicks = Piece.I_COUNTERCLOCKWISE_WALL_KICKS[currentPiece.getRotationIndex()];
                } else {
                    kicks = Piece.NORMAL_COUNTERCLOCKWISE_WALL_KICKS[currentPiece.getRotationIndex()];
                }
                for (int i = 0; i < kicks.length; i++) {
                    nextPiecePosition = new Point((int) (currentPiecePosition.getX() + kicks[i].getX()),
                            (int) (currentPiecePosition.getY() + kicks[i].getY()));
                    try {
                        nextPiece(currentPiece.counterclockwisePiece(), nextPiecePosition);
                    } catch (IllegalArgumentException ex) {
                        continue;
                    }
                    lastResult = Result.SUCCESS;
                    break;
                }
                if (lastResult != Result.SUCCESS) {
                    return lastResult;
                }
                break;
            } case NOTHING:
                break;
        }
        if(currentPiecePosition.getY() == dropHeight(currentPiece, (int) currentPiecePosition.getX())){
            placePiece();
            lastResult = Result.PLACE;
            return lastResult;
        }
        lastResult = Result.SUCCESS;
        return lastResult;
    }

    private void placePiece(){
        for(int i = 0; i < currentPiece.getBody().length; i++){
            grid[(int) (currentPiecePosition.getX() + currentPiece.getBody()[i].getX())]
                    [(int) (currentPiecePosition.getY() + currentPiece.getBody()[i].getY())] = currentPiece.getType();
        }
        updateRowWidth();
        updateColumnHeight();
        currentPiece = null;
        currentPiecePosition = null;
    }

    private void updateColumnHeight(){
        for(int x = 0; x < getWidth(); x++){
            columnHeight[x] = 0;
            for(int y = getHeight() - 1; y >= 0; y--){
                if(grid[x][y] != null){
                    columnHeight[x] = y + 1;
                    break;
                }
            }
        }
    }
    private void updateRowWidth(){
        rowsCleared = 0;
        for(int y = 0; y < getHeight(); y++){
            rowWidth[y] = 0;
            for(int x = 0; x < getWidth(); x++){
                if(grid[x][y] != null){
                    rowWidth[y]++;
                }
            }
            if(rowWidth[y] == 0){
                break;
            }
            if(rowWidth[y] == getWidth()){
                deleteRow(y);
                y--;
            }
        }
    }

    private void deleteRow(int row){
        rowsCleared++;
        for(int y = row + 1; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                grid[x][y - 1] = grid[x][y];
            }
        }
    }

    @Override
    public Board testMove(Action act) {
        Board testBoard = new TetrisBoard(this);
        testBoard.move(act);
        return testBoard;
    }

    @Override
    public Piece getCurrentPiece() {
        return currentPiece;
    }

    @Override
    public Point getCurrentPiecePosition() {
        if(currentPiece == null){
            return null;
        }
        return currentPiecePosition;
    }

    @Override
    public void nextPiece(Piece p, Point spawnPosition)
            throws IllegalArgumentException {
        if ((spawnPosition.getX() < 0 &&
                        p.getSkirt()[(int) spawnPosition.getX() * (-1) - 1] != Integer.MAX_VALUE) ||
                (spawnPosition.getX() + p.getWidth() > getWidth() &&
                        p.getSkirt()[p.getSkirt().length - (int) spawnPosition.getX() - p.getWidth() + getWidth()] != Integer.MAX_VALUE) ||
                //spawnPosition.getY() < 0 ||
                spawnPosition.getY() + p.getHeight() > getHeight() ||
                dropHeight(p, (int) spawnPosition.getX()) > spawnPosition.getY()){
            throw new IllegalArgumentException();
        }

        this.currentPiece = p;
        this.currentPiecePosition = spawnPosition;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof TetrisBoard)) return false;
        TetrisBoard otherBoard = (TetrisBoard) other;
        if(!currentPiece.equals(otherBoard.getCurrentPiece()) ||
                !currentPiecePosition.equals(otherBoard.getCurrentPiecePosition())){
            return false;
        }
        for(int x = 0; x < getWidth(); x++){
            for(int y = 0; y < getHeight(); y++){
                if (grid[x][y] != otherBoard.getGrid(x, y)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Result getLastResult() {
        return lastResult;
    }

    @Override
    public Action getLastAction() {
        return lastAction;
    }

    @Override
    public int getRowsCleared() {
        return rowsCleared;
    }

    @Override
    public int getWidth() {
        return grid.length;
    }

    @Override
    public int getHeight() {
        return grid[0].length;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public int dropHeight(Piece piece, int x) {
        int maxHeight = -2;
        for(int col = 0; col < piece.getWidth(); col++){
            if(piece.getSkirt()[col] != Integer.MAX_VALUE &&
                    maxHeight < getColumnHeight(col + x) - piece.getSkirt()[col]) {
                maxHeight = getColumnHeight(col + x) - piece.getSkirt()[col];
            }
        }
        return maxHeight;
    }

    @Override
    public int getColumnHeight(int x) {
        return columnHeight[x];
    }

    @Override
    public int getRowWidth(int y) {
        return rowWidth[y];
    }

    @Override
    public Piece.PieceType getGrid(int x, int y) {
        return grid[x][y];
    }
}
