package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Bishop extends ChessPiece {
    /**
     * **********************************************************************
     * Constructors
     * ************************************************************************
     */
    public Bishop() {
        super();
    }

    public Bishop(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(Position p) {
        return isAnyObstacleDiagonally(p);
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return (!(p.getRow() == getRow() && p.getCol() == getCol())) && isValidLocation(p) &&
                isInSameDiagonal(p) && !isAnyObstacle(p);
    }

    /*
    * need to check only the diagonal elements
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 1; i < ChessBoard.ROW_BOUNDARY; i++) {
            for (int iterator1 = -1; iterator1 < 2; iterator1 = iterator1 + 2) {
                for (int iterator2 = -1; iterator2 < 2; iterator2 = iterator2 + 2) {
                    Position p = new Position(getRow() + i * iterator1, getCol() + i * iterator2);
                    if (this.isValidMovement(p)) {
                        positions.add(p);
                    }
                }
            }
        }
        return positions;
    }
}
