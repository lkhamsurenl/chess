package Model;

import javafx.geometry.Pos;

/**
 * Created by luvsandondov on 9/11/14.
 */
public class Position {
    /*
    * Helper class to facilitate location by creating (row, column) pair
    * */
    private int row;
    private int col;

    public Position() {
        row = -1;
        col = -1;
    }
    public Position(int x, int y) {
        row = x;
        col = y;
    }
    public Position(Position p) {
        row = p.getRow();
        col = p.getCol();
    }

    /*
    * Get methods.
    * */
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    /*
    * Set methods.
    * */
    public void setRow(int x) {
        row = x;
    }
    public void setCol(int y) {
        col = y;
    }
    public void setPosition(int x, int y) {
        row = x;
        col = y;
    }
    public void setPosition(Position p) {
        row = p.getRow();
        col = p.getCol();
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
