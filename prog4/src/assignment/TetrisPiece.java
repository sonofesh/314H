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
        // TODO: Implement me.
        this.type = type;
        this.body = this.type.getSpawnBody();
        setSkirt();

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

    private Piece clockwisePiece(Piece input){
        Point[] clockwiseBody = new Point[input.getBody().length];
        for(int i = 0; i < input.getBody().length; i++){
            clockwiseBody[i] = new Point((int) input.getBody()[i].getY(),
                    input.getHeight() - (int) input.getBody()[i].getX() - 1);
        }
        return new TetrisPiece(input.getType(), clockwiseBody,input.getRotationIndex() + 1);
    }

    private void setClockwisePiece(Piece input){
        ((TetrisPiece) input).setCounterclockwisePiece(this);
        this.clockwisePiece = input;
    }

    private void setCounterclockwisePiece(Piece input){
        this.counterclockwisePiece = input;
    }

    @Override
    public PieceType getType() {
        // TODO: Implement me.
        return type;
    }

    @Override
    public int getRotationIndex() {
        // TODO: Implement me.
        return rotationIndex;
    }

    @Override
    public Piece clockwisePiece() {
        // TODO: Implement me.
        return clockwisePiece;
    }

    @Override
    public Piece counterclockwisePiece() {
        // TODO: Implement me.
        return counterclockwisePiece;
    }

    @Override
    public int getWidth() {
        // TODO: Implement me.
        return type.getBoundingBox().width;
    }

    @Override
    public int getHeight() {
        // TODO: Implement me.
        return type.getBoundingBox().height;
    }

    @Override
    public Point[] getBody() {
        // TODO: Implement me.
        return body;
    }

    @Override
    public int[] getSkirt() {
        // TODO: Implement me.
        return skirt;
    }

    @Override
    public boolean equals(Object other) {
        // Ignore objects which aren't also tetris pieces.
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;

        // TODO: Implement me.
        return this.type == otherPiece.getType() &&
                this.rotationIndex == otherPiece.getRotationIndex();
    }
}
