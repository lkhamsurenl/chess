package Model;

import Pieces.ChessPiece;
import Pieces.King;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class ChessBoard {
    // number of columns and rows
    public static final int ROW_BOUNDARY = 8;
    public static final int COL_BOUNDARY = 8;

    // Location of the white king
    public static Position white_king_position;
    // Location of the black king
    public static Position black_king_position;

    /**
     * Consists of Chess Pieces in each node. null means no piece in there
     * Note: I could also represent this as a 2 arrayLists, but finding element is not O(1)
     */
    public static ChessPiece[][] ChessBoard;

    // Keep track of removed pieces for each player
    public static ArrayList<ChessPiece> removedWhitePieces;
    public static ArrayList<ChessPiece> removedBlackPieces;

    public ChessBoard() {
        ChessBoard = new ChessPiece[ROW_BOUNDARY][COL_BOUNDARY];
        removedWhitePieces = new ArrayList<ChessPiece>();
        removedBlackPieces = new ArrayList<ChessPiece>();
    }

    /*
    * Function checks if the game is finished. If either side is lost, then the game is finished
    * */
    public boolean isGameFinished() {
        return isTheSideLost(true) || isTheSideLost(false);

    }

    /*
    * For specific color, check first if the king is in Check, then go through all alias pieces and check if they can move
    * NOTE: If there is any piece that can move, it means it can get the king out of its check, since we also check if the move is valid
    * @param color -- Which side white or black
    * */
    public boolean isTheSideLost(boolean color) {
        // Get the king location of the current side
        Position king_pos = white_king_position;
        if (!color) {
            king_pos = black_king_position;
        }
        King king = (King) ChessBoard[king_pos.getRow()][king_pos.getCol()];
        if (king.isInCheck()) {
            for (int i = 0; i < ROW_BOUNDARY; i++) {
                for (int j = 0; j < COL_BOUNDARY; j++) {
                    ChessPiece piece = ChessBoard[i][j];
                    // there is at least one possible movement, then the game is not finished
                    if (piece != null && piece.isWhite() == color && piece.possibleMovements().size() != 0) {
                        // we found at least one movement, which can get the king out of its check
                        // Because in my possible movements, I only consider ones, which leave king not in check
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
