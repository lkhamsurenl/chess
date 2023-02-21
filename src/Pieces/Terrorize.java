package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/15/14.
 * <p/>
 * This piece can do movement depending on its count variable:
 * count %3 ==0 it acts like queen
 * count %3 ==1 it acts like knight
 * count %3 ==2 it can move to any location, but cannot eat anything
 * Whenever there is a movement happened, the count will increase by one
 * <p/>
 * WARNING!!!!!!!!!!!!!!!!
 * NOTE: The only other thing we have to do is update counter in MoveTo() function in ChessPice class
 * WARNING!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Terrorize extends ChessPiece {
    /*
    * private member count figures out which movement to do
    * */
    private int count;

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Terrorize() {
        super();
        count = 0;
    }

    public Terrorize(boolean color, Position p, int count, ChessBoard chessBoard) {
        super(color, p, chessBoard);
        this.count = count;
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(Position p) {
        // Queen move
        if (count % 3 == 0) {
            return isAnyObstacleHorizontally(p) || isAnyObstacleVertically(p) || isAnyObstacleDiagonally(p);
        } else if (count % 3 == 1) {
            // Knight movement
            return isAliasPieceInLocation(p);
        } else {
            // CAn move anywhere with no piece
            return isAnyPieceInLocation(p);
        }
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(p) && isLegalTerrorizeMove(p) && !isAnyObstacle(p);
    }

    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < ChessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < ChessBoard.COL_BOUNDARY; j++) {
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
    public int getCounter(){
        return this.count;
    }
    public void incCounter(){
        this.count++;
    }
    /*
    * Figures out if the movement is legal piece movement
    * */
    private boolean isLegalTerrorizeMove(Position p) {
        if (count % 3 == 0) {
            // Queen move
            return isLegalQueenMove(p);
        } else if (count % 3 == 1) {
            //check if Pieces.Knight movement
            return isLegalKnightMove(p);
        } else {
            // since it can get anywhere
            // NOTE: we check if there is any piece in the destination with isAnyObstacle function
            return true;
        }
    }

    /*
    * Figures out if the move is a legal Pieces.Queen move
    * */
    private boolean isLegalQueenMove(Position p) {
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            return (isInSameCol(p.getCol()) || isInSameRow(p.getRow()) || isInSameDiagonal(p));
        }
        return false;
    }

    /*
    * It's valid knight move, if exactly distance 3 and not in same row or column
    * @param x -- row
    * @param y --column
    * */
    private boolean isLegalKnightMove(Position p) {
        //cannot be in a same position
        if (!(p.getRow() == getRow() && p.getCol() == getCol())) {
            int distance = Math.abs(p.getRow() - getRow()) + Math.abs(p.getCol() - getCol());
            return ((distance == 3) && !(isInSameCol(p.getCol()) || isInSameRow(p.getRow())));
        }
        return false;
    }

}
