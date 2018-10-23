package assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JBrainTetris extends JTetris{

    public static void main(String[] args) {
        createGUI(new JBrainTetris());
    }

    JBrainTetris() {
        super();
        setPreferredSize(new Dimension(WIDTH*PIXELS+2, (HEIGHT+TOP_SPACE)*PIXELS+2));
        gameOn = false;

        board = new TetrisBoard(WIDTH, HEIGHT + TOP_SPACE);

        // Create the Timer object and have it send
        // tick(DOWN) periodically
        PatelBrain PB = new PatelBrain();
        timer = new javax.swing.Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.Action act = PB.nextMove(board);
                tick(act);
                if(board.getMaxHeight() > board.getHeight() - TOP_SPACE){
                    stopGame();
                }
            }
        });
    }
}
