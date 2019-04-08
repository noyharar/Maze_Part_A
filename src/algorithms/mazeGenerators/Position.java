package algorithms.mazeGenerators;

import java.util.Objects;

public class Position {
    private int row;
    private int col;
    private Position myParentPos;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(int row, int col, Position pos) {
        this.row = row;
        this.col = col;
        myParentPos = pos;
    }
    public Position(Position pos) {
        this.row = pos.row;
        this.col = pos.col;
        myParentPos = pos.myParentPos;
    }

    public Position getMyParentPos() {
        return myParentPos;
    }

    @Override
    public String toString()
    {
        return String.format("{%s,%s}", row, col);
    }



    public boolean equals(Position pos){
        return this.row == pos.getRowIndex() && this.col == pos.getColumnIndex();
    }

    public boolean isEqual(int row, int col)
    {
        return this.row == row && this.col == col;
    }

    public int getRowIndex() {

        return row;
    }

    /**
     * @return the col's position
     */
    public int getColumnIndex() {
        return col;
    }

    /**
     * Set col
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Set row
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }


    /**
     * @return the opposite side of my parent
     */
    public Position getOtherSide(int height, int width)
    {
        int myRow= row - myParentPos.row, myCol= col - myParentPos.col, posRow = row + myRow, posCol = col + myCol;

        //if I came from different row
        if( posRow >= 0 &&  posCol >= 0 && posRow < height && posCol < width)
        {
            if(myRow != 0)
            {
                return new Position(posRow,col, this);
            }
            //if I came from different col
            if(myCol != 0)
            {
                return new Position(row,posCol, this);
            }
        }

        return null;

    }
}
