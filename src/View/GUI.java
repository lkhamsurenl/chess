package View;

import Model.ChessBoard;
import Model.Position;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/17/14.
 * Represents View.GUI part of the game, and representing the pieces in the board
 */
public class GUI extends JPanel{

    // Some static constants
    private static int frame_margin_width = View.frame_margin_width;
    private static int frame_margin_height = View.frame_margin_height;

    private static int _ROW = ChessBoard.ROW_BOUNDARY;
    private static int _COL = ChessBoard.COL_BOUNDARY;
    private static int ROW_TIMES_COL = _ROW * _COL;
    // My Pieces.King and Pieces.Pawn pics are slightly off in width
    private static int pawn_width_offset = 10;
    private static int king_width_offset = -7;
    private static int terrorize_offset = 7;
    private static int nightRider_offset = 4;

    public static int squareSize = 64;
    /*
    * Mouse related constants
    * */

    public static ArrayList<Position> positions;
    // Keeps track of highlighting positions
    //Every time repaint called, it will check it and highlight
    //private static ArrayList<Model.Position> positions = null;
    //private static Pieces.ChessPiece pickedPiece = null;

    /**********************************************************************************
     * Private member variables
     ************************************************************************************/

    private ChessBoard chessBoard;
    private Image img;
    private Image other_img;

    /**************************************************************************************
     * Constructors
     ************************************************************************************/
    public GUI() {
        chessBoard = null;
        img = null;
        other_img = null;
    }
    /*
    * @param chessBoard -- Chessboard it's drawing from
    * @param image -- image it gets its pieces
    * */
    public GUI(ChessBoard chessBoard, Image image, Image other_img) {
        this.chessBoard = chessBoard;
        img = image;
        this.other_img = other_img;
    }


    /*
    * Function to draw the components
    * */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(153,67,24));
        // draw board squares
        drawBoard(g);

        //Depending on the which piece it's referring, it will draw the piece
        for (int row = 0; row < ChessBoard.ROW_BOUNDARY; row++) {
            for (int col = 0; col < ChessBoard.COL_BOUNDARY; col++) {
                int j = -1, k = -1;

                ChessPiece piece = chessBoard.ChessBoard[row][col];
                boolean isRegularPiece = isRegularPiece(piece);
                // This is where I figure out which piece I am referring to
                Position position = getPicturePosition(piece);
                j = position.getRow();
                k = position.getCol();
                if(isRegularPiece) {
                    drawRegularPiece(g, j, k, row, col);
                }
               else {
                    drawCustomPiece(g, piece, j, k, row, col);
                }
            }
        }
        // Highlight the necessary squares:
        if(positions !=null) {
            highlightPossiblePositions(g);
        }
    }
    private boolean isRegularPiece(ChessPiece piece) {
        if(piece instanceof Terrorize || piece instanceof NightRider) {
            return false;
        }
        return true;
    }
    private void drawRegularPiece(Graphics g, int j, int k, int row, int col) {
        if(j ==0) {
            // Pieces.King is slightly off
            g.drawImage(img, frame_margin_width + king_width_offset + (col) * squareSize,
                             frame_margin_height + (row) * squareSize,frame_margin_width + (col + 1) * squareSize,
                             frame_margin_height + (row + 1) * squareSize,j * ROW_TIMES_COL, k * ROW_TIMES_COL,
                             (j + 1) * ROW_TIMES_COL, (k + 1) * ROW_TIMES_COL, this);
        }
        else if(j == 5) {
            // then it's pawn, so add some offset
            g.drawImage(img, frame_margin_width + pawn_width_offset + (col) * squareSize,
                            frame_margin_height + (row) * squareSize,frame_margin_width + (col + 1) * squareSize,
                            frame_margin_height + (row + 1) * squareSize,j * ROW_TIMES_COL, k * ROW_TIMES_COL,
                            (j + 1) * ROW_TIMES_COL, (k + 1) * ROW_TIMES_COL, this);
        }
        else if (j != -1 && k != -1) {
            g.drawImage(img, frame_margin_width + (col) * squareSize,
                            frame_margin_height + (row) * squareSize,frame_margin_width + (col + 1) * squareSize,
                            frame_margin_height + (row + 1) * squareSize,j * ROW_TIMES_COL, k * ROW_TIMES_COL,
                            (j + 1) * ROW_TIMES_COL, (k + 1) * ROW_TIMES_COL, this);
        }
    }
    private void drawCustomPiece(Graphics g, ChessPiece piece,  int j, int k, int row, int col) {
        if(piece instanceof Terrorize && piece.isWhite()) {
            g.drawImage(other_img, frame_margin_width + (col) * squareSize + terrorize_offset,
                                frame_margin_height + (row) * squareSize + terrorize_offset ,
                                frame_margin_width + (col + 1) * squareSize - terrorize_offset,
                                frame_margin_height + (row + 1) * squareSize - terrorize_offset,
                                206 * 5 - 15, 0, 206*6 -15, 338, this);
        }
        else if(piece instanceof Terrorize && !piece.isWhite()) {
            g.drawImage(other_img, frame_margin_width + (col) * squareSize + terrorize_offset,
                                frame_margin_height + (row) * squareSize + terrorize_offset ,
                                frame_margin_width + (col + 1) * squareSize - terrorize_offset,
                                frame_margin_height + (row + 1) * squareSize - terrorize_offset ,
                                206 * 0  + 15, 338, 206 * 1  +15, 676, this);
        }
        if(piece instanceof NightRider && piece.isWhite()) {
            g.drawImage(other_img, frame_margin_width + (col) * squareSize + nightRider_offset,
                                frame_margin_height + (row) * squareSize + nightRider_offset,
                                frame_margin_width + (col + 1) * squareSize - nightRider_offset,
                                frame_margin_height + (row + 1) * squareSize - nightRider_offset,
                                206 * 2 , 0, 206 * 3 , 338, this);
        }
        else if(piece instanceof NightRider && !piece.isWhite()) {
            g.drawImage(other_img, frame_margin_width + (col) * squareSize + nightRider_offset,
                                frame_margin_height + (row) * squareSize + nightRider_offset,
                                frame_margin_width + (col + 1) * squareSize - nightRider_offset,
                                frame_margin_height + (row + 1) * squareSize - nightRider_offset,
                                206 * 3, 338, 206*4 , 676, this);
        }
    }
    /*
    * Figures out which piece it is in the picture
    * */
    private Position getPicturePosition(ChessPiece piece) {
        int k = -1, j = -1;
        if (piece instanceof Pawn && piece.isWhite()) {
            j = 5;
            k = 0;
        } else if (piece instanceof Pawn && !piece.isWhite()) {
            j = 5;
            k = 1;
        } else if (piece instanceof Rook && piece.isWhite()) {
            j = 2;
            k = 0;
        } else if (piece instanceof Rook && !piece.isWhite()) {
            j = 2;
            k = 1;
        } else if (piece instanceof Knight && piece.isWhite()) {
            j = 4;
            k = 0;
        } else if (piece instanceof Knight && !piece.isWhite()) {
            j = 4;
            k = 1;
        } else if (piece instanceof Bishop && piece.isWhite()) {
            j = 3;
            k = 0;
        } else if (piece instanceof Bishop && !piece.isWhite()) {
            j = 3;
            k = 1;
        } else if (piece instanceof Queen && piece.isWhite()) {
            j = 1;
            k = 0;
        } else if (piece instanceof Queen && !piece.isWhite()) {
            j = 1;
            k = 1;
        } else if (piece instanceof King && piece.isWhite()) {
            j = 0;
            k = 0;
        } else if (piece instanceof King && !piece.isWhite()) {
            j = 0;
            k = 1;
        }
        return new Position(j,k);
    }

    /*
     * Fills up the square with alternating color
     * ****************************************
     *  This part of the code has been borrowed from "Simple Chess Engine, Youtube Tutorial".
     * ****************************************
     * */
    private void drawBoard(Graphics g) {
        for (int i = 0; i < ROW_TIMES_COL; i += 2) {
            g.setColor(new Color(234,206,164));
            g.fillRect(frame_margin_height + (i % _COL + (i / _ROW) % 2) * squareSize,
                        frame_margin_height + (i / _ROW) * squareSize, squareSize, squareSize);
            g.setColor(new Color(118,52,15));
            g.fillRect(frame_margin_height + ((i + 1) % _COL - ((i + 1) / _ROW) % 2) * squareSize,
                        frame_margin_height +  ((i + 1) / _ROW) * squareSize, squareSize, squareSize);
        }
    }

    /*
    * Highlights the possible movements for a selected piece in the board.
    * */
    private void highlightPossiblePositions(Graphics g) {
        for(Position p: positions) {
            int row = p.getRow();
            int col = p.getCol();
            // transparent color
            g.setColor(new Color(0,255,255, 50));
            // x, y, width, height
            g.fillRect(frame_margin_width + col * squareSize, frame_margin_height + row * squareSize,
                    squareSize, squareSize);
        }
    }
}
