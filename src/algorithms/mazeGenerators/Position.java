package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int col;


    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString()
    {
        return String.format("{%s,%s}", row, col);
    }

    public boolean isEqual(int row, int col){
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
}
