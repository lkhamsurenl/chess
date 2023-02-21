package Pieces;

import Model.ChessBoard;
import Model.Position;
import View.View;

import java.util.ArrayList;

/**
 * The abstract class representing a chess piece, shared across all the instances of the chess pieces.
 * Created by luvsandondov on 9/8/14.
 */
public abstract class ChessPiece {
    /**
     * *************************************************************************
     * Class private variables representing pieces
     * *****************************************************************************
     */
    // 1 = white, 0=black
    private boolean isWhite;
    // Current position of the piece.
    private Position position;
    // chessBoard the piece is on.
    ChessBoard chessBoard;

    /**
     * *************************************************************************
     * Constructors
     * *****************************************************************************
     */
    public ChessPiece() {
        this.isWhite = true;
        this.position = new Position();
        // We also need Model.ChessBoard instance in which one it is referring.
        chessBoard = null;
    }

    // Set the piece
    public ChessPiece(boolean color, Position p, ChessBoard chessBoard) {
        this.isWhite = color;
        this.position = new Position(p);
        if (isValidLocation(p)) {
            // Initialize the piece position in the board.
            chessBoard.ChessBoard[p.getRow()][p.getCol()] = this;
        }
    }

    /**
     * *************************************************************************
     * Abstract methods
     * *****************************************************************************
     */
    // Figure out if there is any obstacle to reach the destination.
    public abstract boolean isAnyObstacle(Position p);

    // Check if the given location is reachable by the piece and no piece in the way.
    abstract boolean isReachable(Position p);

    // Get all possible movements for the piece.
    public abstract ArrayList<Position> possibleMovements();

    /**
     * *************************************************************************
     * Public functions
     * *****************************************************************************
     */
    public boolean isWhite() { return this.isWhite; }

    public int getRow() {
        return this.position.getRow();
    }

    public int getCol() {
        return this.position.getCol();
    }

    public ChessBoard getChessBoard() {return chessBoard; }

    /*
    * Set the new position of the piece, useful for debugging.
    * */
    public void setPosition(Position p) {
        // Remove the piece from previous location.
        chessBoard.ChessBoard[this.position.getRow()][this.position.getCol()] = null;
        // Update current location.
        this.position.setPosition(p.getRow(), p.getCol());
        // Update the position of the King, if the current piece is a king.
        if (this instanceof King) {
            if (isWhite()) {
                chessBoard.white_king_position = this.position;
            } else {
                chessBoard.black_king_position = this.position;
            }
        }
        if(isValidLocation(p)) {
            // Update the board again withe piece new location.
            chessBoard.ChessBoard[this.position.getRow()][this.position.getCol()] = this;
        }
    }


    // Check if the given location is not out of boundary.
    public boolean isValidLocation(Position p) {
        return 0 <= p.getRow() && p.getRow() < chessBoard.ROW_BOUNDARY &&
                0 <= p.getCol() && p.getCol() < chessBoard.COL_BOUNDARY;
    }

    // Determine if given movement is valid by no obstacles, and wouldn't give up the king.
    public boolean isValidMovement(Position p) {
        boolean value = false;
        if (isReachable(p)) {
            Position old_pos = new Position(this.position);
            ChessPiece temp_removed_piece = tryPosition(p);
            // Check if king is not threatened by any piece from other side, otherwise the movement is not valid.
            King aliasKing = isWhite() ? (King) chessBoard.ChessBoard[chessBoard.white_king_position.getRow()]
                                                                        [chessBoard.white_king_position.getCol()] :
                    (King) chessBoard.ChessBoard[chessBoard.black_king_position.getRow()]
                            [chessBoard.black_king_position.getCol()];
            if (!aliasKing.isInCheck()) {
                value = true;
            }
            revertPosition(old_pos, temp_removed_piece);
        }
        return value;
    }

    // Move to the specific location, by capturing piece if there is one and setPosition.
    public void moveTo(Position p) {
        if (isValidMovement(p)) {
            ChessPiece removedPiece = null;
            // Update the position.
            if (chessBoard.ChessBoard[p.getRow()][p.getCol()] != null) {
                 removedPiece = chessBoard.ChessBoard[p.getRow()][p.getCol()];
            }
            // Add it to the removed piece collector.
            View.queryRemovedPiece(this, removedPiece, getRow(), getCol());

            setPosition(p);
            // Pawn is no longer in a initial state.
            if(this instanceof Pawn) {
                Pawn pawn = (Pawn) this;
                if(pawn.getInitState()) pawn.setInitState(false);
            }
            // Terrorize's counter will increase as it moves.
            else if(this instanceof Terrorize) {
                ((Terrorize) this).incCounter();
            }
        } else {
            // Then moveTo cannot be done
            System.out.println("Cannot Move To the Specified location: " + p);
            System.exit(-1);
        }
    }

    // Check if particular piece can directly eat opponent's king.
    public boolean canDirectlyEatKing() {
        // Get the opposite Pieces.King's location
        Position p = this.isWhite ? chessBoard.black_king_position : chessBoard.white_king_position;
        if (this instanceof Pawn) {
            // Then we  have to check if it's actually can eat king different way than other pieces.
            if (((Pawn) this).isTryingEatingOther(p)) {
                return true;
            } else
                return false;
        } else {
            // If it is reachable, then it is eatable.
            return isReachable(p);
        }
    }

    /**
     * *********************************************************************************************************
     * Helper functions
     * **********************************************************************************************************
     */
    // Figures out if there is any piece in certain location, assume x,y is valid Location.
    public boolean isAliasPieceInLocation(Position p) {
        ChessPiece piece = chessBoard.ChessBoard[p.getRow()][p.getCol()];
        return (piece != null && piece.isWhite() == this.isWhite());
    }

    public boolean isAnyPieceInLocation(Position p) {
        ChessPiece piece = chessBoard.ChessBoard[p.getRow()][p.getCol()];
        boolean value = (piece != null);
        return value;
    }

    public boolean isInSameCol(int y) {
        return (this.getCol() == y);
    }

    public boolean isInSameRow(int x) {
        return (this.getRow() == x);
    }

    public boolean isInSameDiagonal(Position p) {
        return Math.abs(this.getRow() - p.getRow()) == Math.abs(this.getCol() - p.getCol());
    }

    /*
    * Check if there is any obstacle horizontally from current to destination.
    */
    public boolean isAnyObstacleHorizontally(Position p) {
        if (isInSameRow(p.getRow())) {
            // Assume it is horizontally reachable.
            int curr_col = this.getCol();
            int curr_row = this.getRow();
            // Set the iterator to be whichever lower.
            int iterator = curr_col < p.getCol() ? 1 : -1;
            for (int j = 1; j <= Math.abs(p.getCol() - curr_col); j++) {
                Position pos = new Position(curr_row, curr_col + j * iterator);
                if (j == Math.abs(p.getCol() - curr_col)) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(pos)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Figures out if there is any obstacle diagonally from current to destination.
    public boolean isAnyObstacleVertically(Position p) {
        if (isInSameCol(p.getCol())) {
            // Assume it is vertically reachable.
            int curr_col = this.getCol();
            int curr_row = this.getRow();
            // Set the iterator to be whichever lower.
            int iterator = curr_row < p.getRow() ? 1 : -1;
            for (int i = 1; i <= Math.abs(p.getRow() - curr_row); i++) {
                Position pos = new Position(curr_row + i * iterator, curr_col);
                if (i == Math.abs(p.getRow() - curr_row)) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(pos)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Figures out if there is any obstacle diagonally from current to destination to reach the given location.
    public boolean isAnyObstacleDiagonally(Position p) {
        if (isInSameDiagonal(p)) {
            int curr_col = this.getCol();
            int curr_row = this.getRow();
            // Set the iterator to be whichever lower.
            int iterator1 = curr_row < p.getRow() ? 1 : -1;
            int iterator2 = curr_col < p.getCol() ? 1 : -1;
            for (int i = 1; i <= Math.abs(p.getRow() - curr_row); i++) {
                Position pos = new Position(curr_row + i * iterator1, curr_col + i * iterator2);
                if ((i == Math.abs(p.getRow() - curr_row)) && (i == Math.abs(p.getCol() - curr_col))) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(pos)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    * Try this new position temporarily, so do not delete the piece.
    * */
    private ChessPiece tryPosition(Position p) {
        ChessPiece temp_removed_piece = null;
        if(chessBoard.ChessBoard[p.getRow()][p.getCol()] != null) {
            temp_removed_piece = chessBoard.ChessBoard[p.getRow()][p.getCol()];
        }
        this.setPosition(p);
        return temp_removed_piece;
    }

    /*
    * Put back the piece into its row, col position and put any piece previously been in the tried position.
    * */
    private void revertPosition(Position p, ChessPiece temp_removed_piece) {
        Position old_loc = new Position(this.position);
        this.setPosition(p);
        if(temp_removed_piece!=null) {
            // Put this piece back in its location.
            temp_removed_piece.setPosition(old_loc);
        }
    }
}