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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col &&
                Objects.equals(myParentPos, position.myParentPos);
    }
/*
    public boolean equals(Position pos){
        return this.row == pos.getRowIndex() && this.col == pos.getColumnIndex();
    }
    */
    public boolean isEqual(int row, int col)
    {
        return this.row == row && this.col == col;
    }

    public int getRowIndex() {

        return row;
    }

    public int getColumnIndex() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }


    public Position getOtherSide()
    {
        int myRow= row - myParentPos.row;
        int myCol= col - myParentPos.col;
        if(myRow != 0)
        {
           return new Position(row + myRow,col, this);
        }
        if(myCol != 0)
        {
            return new Position(row,col + myCol, this);
        }

        return null;

    }
}
