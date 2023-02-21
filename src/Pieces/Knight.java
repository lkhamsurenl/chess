package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Knight extends ChessPiece {
    public Knight() {
        super();
    }

    public Knight(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    /*
    * Only obstacle is when there is alias piece in the destination
    * NOTE: This method will be only called when the movement is valid
    * */
    @Override
    public boolean isAnyObstacle(Position p) {
        return isAliasPieceInLocation(p);
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement for particular piece
        return isValidLocation(p) && isLegalKnightMove(p) && !isAnyObstacle(p);
    }

    /*
    * Pieces.Knight can move distance at most 3, so check for all possibilities
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                Position p = new Position(getRow() + i, getCol() + j);
                //we need to check if the movement can be made without losing the king
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
    * It's valid knight move, if exactly distance 3 and not in same row or column
    * */
    public boolean isLegalKnightMove(Position p) {
        //cannot be in a same position
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            int distance = Math.abs(p.getRow() - getRow()) + Math.abs(p.getCol() - getCol());
            return ((distance == 3) && !(isInSameCol(p.getCol()) || isInSameRow(p.getRow())));
        }
        return false;
    }

}
