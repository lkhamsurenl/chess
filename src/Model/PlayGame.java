package Model;

import Controller.Player;
import Pieces.*;
import View.View;
import javafx.geometry.Pos;

import javax.swing.*;
import java.util.HashMap;

public class PlayGame {

    // Keep track of all types of different players, and their associated scores
    public static HashMap<String, Integer> playersList;
    // Determine which player's turn
    public static boolean turn = true;
    // Determines if a game has started
    public static boolean START = false;
    // Determines if a game has finished
    public static boolean isGameFinished = false;
    // Keep track of the current players
    private static Player player1;
    private static Player player2;


    /**
     * **************************************************************************************
     * Main function where you can actually play the game
     * ***************************************************************************************
     */
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        // Controller.Player name is associated with the score.
        // TODO(luvsandondov): Add AI component so that the player can play against a computer.
        View frame = new View("Chess Game", chessBoard);
        player1 = frame.getPlayer(true);
        player2 = frame.getPlayer(false);

        playersList = new HashMap<String, Integer>();

        Thread t = new Thread(new MainLoop());
        t.start();
    }

    // Instead of busy waiting, sleep.
    private static class MainLoop implements Runnable {
        public void run() {
            try {
                long gameTime = 10000;
                while (true)
                    Thread.sleep(gameTime);
            } catch (Exception e) {
            }
        }
    }


    public static void finishGame(ChessBoard chessBoard, JFrame frame) {
        Player winner = chessBoard.isTheSideLost(false) ? player1 : player2;
        JOptionPane.showMessageDialog(frame, winner.getName() + " has won!");
        // Update the score in the system.
        View.incrementWinnerScore(winner);
        View.reset();
        // Then play another game.
        isGameFinished = false;
    }

    /*
    * Everything is in initial condition.
    * */
    public static void initializeChessBoard(Model.ChessBoard chessBoard, boolean isRegularChess) {
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
                Position p = new Position(i, j);
                if (i == 0 || i == 7) {
                    boolean isWhite = (i == 0 ? true : false);
                    if (j == 0 || j == 7) {
                        new Rook(isWhite, p, chessBoard);
                    }
                    if (j == 6) {
                        new Knight(isWhite, p, chessBoard);
                    }
                    if (j == 1 && isRegularChess) {
                        new Knight(isWhite, p, chessBoard);
                    } else if (j == 1 && !isRegularChess) {
                        new NightRider(isWhite, p, chessBoard);
                    }
                    if (j == 2 || j == 5) {
                        new Bishop(isWhite, p, chessBoard);
                    }
                    if (j == 3) {
                        new King(isWhite, p, chessBoard);
                    }
                    if (j == 4) {
                        if (isRegularChess) new Queen(isWhite, p, chessBoard);
                        else new Terrorize(isWhite, p, 0, chessBoard);
                    }
                } else if (i == 1 || i == 6) {
                    boolean isWhite = (i == 1 ? true : false);
                    new Pawn(isWhite, p, chessBoard);
                } else {
                    chessBoard.ChessBoard[i][j] = null;
                }
            }
        }
    }

    public static void removeAllPieces(ChessBoard chessBoard) {
        for (int row = 0; row < ChessBoard.ROW_BOUNDARY; row++) {
            for (int col = 0; col < ChessBoard.COL_BOUNDARY; col++) {
                if (chessBoard.ChessBoard[row][col] != null) {
                    removePiece(chessBoard, chessBoard.ChessBoard[row][col]);
                    chessBoard.ChessBoard[row][col] = null;
                }
            }
        }
    }

    public static void removePiece(ChessBoard chessBoard, ChessPiece piece) {
        if (piece.isWhite()) {
            chessBoard.removedWhitePieces.add(piece);
        } else {
            chessBoard.removedBlackPieces.add(piece);
        }
    }


}

