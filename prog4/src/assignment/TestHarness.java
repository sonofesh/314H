package assignment;

public class TestHarness {
    public static void main(String[] args) {
        Piece TP = new TetrisPiece(Piece.PieceType.T);
        TP = TP.clockwisePiece();
        TP = TP.counterclockwisePiece();
        TP = TP.counterclockwisePiece();
        TP = TP.counterclockwisePiece();

        for(int i = 0; i < TP.getBody().length; i++){
            System.out.println(TP.getBody()[i].getX() + " " + TP.getBody()[i].getY());
        }

        System.out.println();
        System.out.println();

        for(int i = 0; i < TP.getSkirt().length; i++){
            System.out.print(TP.getSkirt()[i] + " ");
        }

    }
}
