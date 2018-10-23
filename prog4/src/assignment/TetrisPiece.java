package assignment;

import java.awt.*;

/**
 * An immutable representation of a tetris piece in a particular rotation.
 * 
 * All operations on a TetrisPiece should be constant time, except for it's
 * initial construction. This means that rotations should also be fast - calling
 * clockwisePiece() and counterclockwisePiece() should be constant time! You may
 * need to do precomputation in the constructor to make this possible.
 */
public final class TetrisPiece implements Piece {
    private PieceType type;
    private int rotationIndex = 0;
    private Piece clockwisePiece;
    private Piece counterclockwisePiece;
    private int[] skirt;
    private Point[] body;

    /**
     * Construct a tetris piece of the given type. The piece should be in it's spawn orientation,
     * i.e., a rotation index of 0.
     * 
     * You may freely add additional constructors, but please leave this one - it is used both in
     * the runner code and testing code.
     */
    public TetrisPiece(PieceType type) {
        this.type = type;
        this.body = this.type.getSpawnBody();
        setSkirt();

        //Sets the clockwise pieces of each of the four pieces using the clockwisePiece method. The clockwise piece of
        //the last element back to the first one, creating a structure that resembles a circularly linked list. Doing us
        //makes it so we don't have to call clockwisePiece every time we need to generate one
        setClockwisePiece(clockwisePiece(this));
        ((TetrisPiece) clockwisePiece).setClockwisePiece(clockwisePiece(clockwisePiece));
        ((TetrisPiece) clockwisePiece.clockwisePiece()).setClockwisePiece(clockwisePiece(clockwisePiece.clockwisePiece()));
        ((TetrisPiece) clockwisePiece.clockwisePiece().clockwisePiece()).setClockwisePiece(this);
    }

    private TetrisPiece(PieceType type, Point[] body, int rotationIndex){
        this.type = type;
        this.body = body;
        if (rotationIndex > 3){
            this.rotationIndex = 0;
        } else {
            this.rotationIndex = rotationIndex;
        }
        setSkirt();
    }

    /**
     * Sets the piece's skirt according to provided specification. For each x value across the piece, the skirt
     * gives the lowest y value in the body relative to the bottom of the SRS
     * bounding box. If there is no block in a given column, the skirt for that column
     * is Integer.MAX_VALUE.
     */
    private void setSkirt(){
        skirt = new int[(int) type.getBoundingBox().getWidth()];
        for(int i = 0; i < skirt.length; i++){
            skirt[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < getBody().length; i++){
            if (getBody()[i].getY() <
                    skirt[(int) getBody()[i].getX()]){
                skirt[(int) getBody()[i].getX()] =
                        (int) getBody()[i].getY();
            }
        }
    }

    /**
     * Returns a piece that is 90 degrees clockwise rotated from this piece,
     * according to the Super Rotation Systems
     * @param input
     */
    private Piece clockwisePiece(Piece input){
        Point[] clockwiseBody = new Point[input.getBody().length];
        for(int i = 0; i < input.getBody().length; i++){
            //uses the mathimatical formula for rotation in a cartesian plane
            clockwiseBody[i] = new Point((int) input.getBody()[i].getY(),
                    input.getHeight() - (int) input.getBody()[i].getX() - 1);
        }
        return new TetrisPiece(input.getType(), clockwiseBody,input.getRotationIndex() + 1);
    }

    /**
     * Sets counterclockwise piece of the input to the instance of the class. Or in other words, it sets the clockwise
     * piece of the instance of the class to the input
     * @param input
     */
    private void setClockwisePiece(Piece input){
        ((TetrisPiece) input).setCounterclockwisePiece(this);
        this.clockwisePiece = input;
    }

    /**
     * Sets the counterclockwise piece of the instance of the class to the input
     * @param input
     */
    private void setCounterclockwisePiece(Piece input){
        this.counterclockwisePiece = input;
    }

    @Override
    public PieceType getType() {
        return type;
    }

    @Override
    public int getRotationIndex() {
        return rotationIndex;
    }

    @Override
    public Piece clockwisePiece() {
        return clockwisePiece;
    }

    @Override
    public Piece counterclockwisePiece() {
        return counterclockwisePiece;
    }

    @Override
    public int getWidth() {
        return type.getBoundingBox().width;
    }

    @Override
    public int getHeight() {
        return type.getBoundingBox().height;
    }

    @Override
    public Point[] getBody() {
        // TODO: Implement me.
        return body;
    }

    @Override
    public int[] getSkirt() {
        return skirt;
    }

    @Override
    public boolean equals(Object other) {
        //Ignore objects which aren't also Tetris pieces.
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;

        return this.type == otherPiece.getType() &&
                this.rotationIndex == otherPiece.getRotationIndex();
    }
}
