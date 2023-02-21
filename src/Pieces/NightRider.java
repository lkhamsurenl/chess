package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/16/14.
 *  Nightrider can make an unlimited number of knight moves (that is, (1,2) cells) in any direction in a straight line
 *  (like other riders, it cannot change direction partway through its move).
 *  Slight modification: it can only make that "jump, if there is no piece in any 1-knight places it'll visit"
 */
public class NightRider extends ChessPiece{
    // factor on how many knight jump it is making
    private int multiplicity = 1;
    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public NightRider() {
        super();
    }

    public NightRider(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
        if(isLegalNightRiderMove(p)) {
            multiplicity = getMultiplicity(p);
        }
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(Position p) {
        multiplicity = getMultiplicity(p);
        // steps of how much taking
        int row_step = (p.getRow() - getRow())/ multiplicity;
        int col_step = (p.getCol() - getCol()) / multiplicity;
        // it's assumed here that movement is valid.
        // we need to check movement validity through checking
        for(int i=1; i <= multiplicity; i++) {
            if(i != multiplicity) {
                if(isAnyPieceInLocation(new Position(getRow() + i * row_step, getCol() + i * col_step))) {
                    return true;
                }
            }
            else {
                // it's the destination position, so we check if it's the alias
                return isAliasPieceInLocation(p);
            }
        }
        return false;
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(p) && isLegalNightRiderMove(p) && !isAnyObstacle(p);
    }

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
    private int getMultiplicity(Position p){
        int mul;
        // absolute difference in col and row
        int abs_row_diff = Math.abs(getRow() - p.getRow());
        int abs_col_diff = Math.abs(getCol() - p.getCol());
        if(abs_row_diff < abs_col_diff) {
            mul= abs_col_diff  -  abs_row_diff;
        }
        else {
            mul = abs_row_diff - abs_col_diff;
        }
        return mul;
    }
    /*
    * Figures out if the movement is legal piece movement
    * */
    private boolean isLegalNightRiderMove(Position p) {
        if(!(p.getRow() == getRow() && p.getCol() == getCol())) {
            int abs_row_diff = Math.abs(getRow() - p.getRow());
            int abs_col_diff = Math.abs(getCol() - p.getCol());
            if(abs_row_diff < abs_col_diff) {
                return (abs_col_diff == abs_row_diff *2);
            }
            else {
                return (abs_row_diff == abs_col_diff *2);
            }
        }
        return false;
    }

}
