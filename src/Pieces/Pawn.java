package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Pawn extends ChessPiece {

    // Determine if it's a initial state
    private boolean isInInitState;

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Pawn() {
        super();
    }
    public Pawn(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
        if (color) {
            // then it's white, so row==1
            isInInitState = (p.getRow() == 1);
        } else {
            // it's black, so row == 6
            isInInitState = (p.getRow() == 6);
        }
    }
    /***********************************************************************************************
     * Useful functoins
     * ********************************************************************************************/
    public boolean getInitState() {
        return isInInitState;
    }

    public void setInitState(boolean currState) {
        isInInitState = currState;
    }

    /***********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************/

    // NOTE: this method called only when the destination is valid movement for the pawn, pawn is the only piece cannot
    // eat thing front of it. So we can safely assume that checking no obstacle diagonally and horizontally are enough,
    // as they are the only possible movements for the pawn
    @Override
    public boolean isAnyObstacle(Position p) {
        // For pawn, even there is opponent piece when moving forward, cannot eat it
        if (isMovingForward(p) && !isAnyPieceInLocation(p)) {
            return false;
        } else if (isJumpTwo(p) && !isAnyObstacleVertically(p) && !isAnyPieceInLocation(p)) {
            return false;
        }
        // then it's trying to eat, so check no obstacle diagonally
        else  if(isTryingEatingOther(p)) {
            return isAnyObstacleDiagonally(p);
        }
        return true;
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        if (isValidLocation(p) && isLegalPawnMove(p)) {
            // Check no alias piece in a way, by checking its alias is obstacling it
            if (!isAnyObstacle(p)) {
                return true;
            }
        }
        return false;
    }

    /*
    * Pieces.Pawn, we need to consider that it can move at most distance to horizontally and 1 vertically
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = -2; i < 3; i++) {
            for (int j = -1; j < 2; j++) {
                Position p = new Position(getRow() + i, getCol() + j);
                // valid movement method checks if there is any obstacle and movement is legal for the piece
                if (isValidMovement(p)) {
                    positions.add(p);
                }
            }
        }
        return positions;
    }

    /**
     * *********************************************************************************************
     * Helper functions specific to the class
     * ********************************************************************************************
     */
    /*
    *  Figures out if it's a legal pawn move
    * */
    public boolean isLegalPawnMove(Position p) {
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            return (isJumpTwo(p) || isMovingForward(p) || isTryingEatingOther(p));
        }
        return false;
    }

    //moving by one
    boolean isMovingForward(Position p) {
        if (isInSameCol(p.getCol())) {
            int indicator = isWhite() ? 1 : -1;
            // if white, then should be moving up; else should be moving down
            return indicator * (p.getRow() - getRow()) == 1;
        }
        return false;
    }

    public boolean isJumpTwo(Position p) {
        // it is first move and same column, then we know it cannot move back
        if (isInInitState && isInSameCol(p.getCol())) {
            int indicator = isWhite() ? 1: -1;
            return indicator * (p.getRow() - getRow() ) == 2;
        }
        return false;
    }

    // Trying to eat other piece diagonally, so there has to be a opponent piece on that location
    public boolean isTryingEatingOther(Position p) {
        boolean isAnyOpponentPiece = !isAliasPieceInLocation(p) && isAnyPieceInLocation(p);
        int indicator = isWhite() ? 1 : -1;
        return ((p.getRow() - getRow()) * indicator == 1) && (Math.abs(p.getCol() - getCol()) == 1) && isAnyOpponentPiece;

    }
}
