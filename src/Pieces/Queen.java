package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Queen extends ChessPiece {
    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Queen() {
        super();
    }

    public Queen(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */

    @Override
    public boolean isAnyObstacle(Position p) {
        return isAnyObstacleHorizontally(p) || isAnyObstacleVertically(p) || isAnyObstacleDiagonally(p);
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(p) && isLegalQueenMove(p) && !isAnyObstacle(p);
    }

    /*
    * Pieces.Queen has much bigger, so we just check for all the cells
    * NOTE: not the optimal way to do this
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
                Position p = new Position(i, j);
                if (isValidMovement(p)) {
                    positions.add(p);
                }
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
* Figures out if the move is a legal Pieces.Queen move
* */
    private boolean isLegalQueenMove(Position p) {
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            return (isInSameCol(p.getCol()) || isInSameRow(p.getRow()) || isInSameDiagonal(p));
        }
        return false;
    }
}
