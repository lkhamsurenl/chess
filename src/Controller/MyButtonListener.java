package Controller;

import Controller.Player;
import Model.ChessBoard;
import Model.PlayGame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by luvsandondov on 9/20/14.
 */
public class MyButtonListener implements MouseListener {

    // Main chess display panel
    private JPanel mainPanel;
    // Current player's panel
    private Player player;
    private ChessBoard chessBoard;
    private JFrame frame;

    /**
     * *********************************************************************************************************
     * Constructors
     * **********************************************************************************************************
     */
    MyButtonListener() {
        frame = null;
        mainPanel = null;
        player = null;
        chessBoard = null;
    }

    public MyButtonListener(JFrame frame, JPanel mainPanel, Player player, ChessBoard chessBoard) {
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.player = player;
        this.chessBoard = chessBoard;


    }

    /**
     * *********************************************************************************************************
     * Mouse interaction functions
     * **********************************************************************************************************
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        String displayName;
        int score;
        // We first show the dialog on the panel
        // prompt the user to enter their name
        String name = JOptionPane.showInputDialog(frame, "What's your name?");
        // Look for this name
        if (PlayGame.playersList.containsKey(name)) {
            // Then it's existing player, we get the score
            displayName = name;
            score = PlayGame.playersList.get(name);
        } else {
            // We add this player, and set score to be 0
            PlayGame.playersList.put(name, 0);
            displayName = name;
            score = 0;
        }
        player.setName(displayName);
        player.setScore(score);
        player.repaint();
        // We restart the game
        PlayGame.removeAllPieces(chessBoard);
        PlayGame.turn = true;
        PlayGame.START = false;
        // Repaint.
        mainPanel.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
