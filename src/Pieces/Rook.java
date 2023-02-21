package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Rook extends ChessPiece {

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Rook() {
        super();
    }

    public Rook(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */

    @Override
    public boolean isAnyObstacle(Position p) {
        return isAnyObstacleHorizontally(p) || isAnyObstacleVertically(p);
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(p) && isLegalRookMove(p) && !isAnyObstacle(p);
    }

    /*
    * For rook, we check horizontal and vertical moves separately
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            Position p = new Position(i, getCol());
            if (isValidMovement(p) && i != getRow()) {
                positions.add(p);
            }
        }

        for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
            Position p = new Position(getRow(), j);
            if (isValidMovement(p) && j != getCol()) {
                positions.add(p);
            }
        }
        return positions;
    }

    /**
     * *********************************************************************************************
     * Helper functions
     * ********************************************************************************************
     */

    /*
    * Figures out if the move is a legal rook move by checking if in same row or column
    * */
    public boolean isLegalRookMove(Position p) {
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            return (isInSameCol(p.getCol()) || isInSameRow(p.getRow()));
        }
        return false;
    }
}
