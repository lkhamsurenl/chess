package Controller;

import Model.ChessBoard;
import Model.PlayGame;
import Model.Position;
import Pieces.ChessPiece;
import View.GUI;
import View.View;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/19/14.
 */
public class MyListener extends MouseInputAdapter{
    private static int mouseX, mouseY, destMouseX, destMouseY;
    private static ChessPiece pickedPiece;
    /************************************************************************************************************
     * Mouse interaction functions
     * ***********************************************************************************************************/
    private JFrame frame;
    private JPanel jpanel;
    private ChessBoard chessBoard;

    public MyListener() {frame = null; jpanel = null; chessBoard = null;}
    public MyListener(JFrame frame, JPanel jpanel, ChessBoard chessBoard) {
        this.frame = frame;
        this.jpanel = jpanel;
        this.chessBoard = chessBoard;
    }
    /*
    * When it is pressed we check its validation
    * */
    @Override
    public void mousePressed(MouseEvent e) {
        if(isClickInBoundary(e.getX() ,e.getY())) {
            mouseX = e.getX();
            mouseY = e.getY();

            //where the move was made from
            int row = (mouseY - View.frame_margin_height) / GUI.squareSize;
            int col = (mouseX - View.frame_margin_width) / GUI.squareSize;
            if(chessBoard.ChessBoard[row][col] != null && chessBoard.ChessBoard[row][col].isWhite() == PlayGame.turn) {
                pickedPiece = chessBoard.ChessBoard[row][col];
                // get actual positions and initialize the position variable
                GUI.positions = getPossiblePositions(pickedPiece);
            }
            jpanel.repaint();
        }
    }

    // We check destination place validity and make move
    @Override
    public void mouseReleased(MouseEvent e) {
        if(isClickInBoundary(e.getX() ,e.getY())) {
            destMouseX = e.getX();
            destMouseY = e.getY();
            // Mouse Button1 is pressed.
            if(e.getButton() == MouseEvent.BUTTON1) {
                // those are the new row and col of the piece
                int newRow = (destMouseY - View.frame_margin_height) / GUI.squareSize;
                int newCol = (destMouseX - View.frame_margin_width) / GUI.squareSize;
                // positions variable has to be initialized by MousePressed event
                ArrayList<Position> positions = GUI.positions;
                // It has our destination location, so we make the move
                if(positions != null) {
                    for(Position position: positions) {
                        if(position.getRow() == newRow && position.getCol() == newCol) {
                            // then we can move there
                            if(pickedPiece.isWhite() == PlayGame.turn) {
                                pickedPiece.moveTo(new Position(newRow, newCol));
                                PlayGame.turn = PlayGame.turn?false:true;
                                PlayGame.isGameFinished = chessBoard.isGameFinished();
                            }
                            // We already did the movement, so clear it up
                            GUI.positions = null;
                            break;
                        }
                    }
                }
            }
            jpanel.repaint();
            if(PlayGame.isGameFinished) {
                PlayGame.finishGame(chessBoard, frame);
            }
            frame.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    /*****************************************************************************************************************
     * Private Helper Functions
     * *************************************************************************************************************/
    /*
    * Initialize the position variable with possible movements
    * */
    private ArrayList<Position> getPossiblePositions(ChessPiece pickedPiece) {
        ArrayList<Position> positions = null;
        if(pickedPiece != null) {
            positions = pickedPiece.possibleMovements();
        }
        return positions;
    }
    // Helper function figures out if the click was within the boundary
    private boolean isClickInBoundary(int mouseX, int mouseY) {
        return View.frame_margin_height < mouseY && (mouseY < View.frame_margin_height + View.board_height) &&
                View.frame_margin_width < mouseX && (mouseX < View.frame_margin_width  + View.board_width);
    }
}
