package algorithms.mazeGenerators;

public class Cells {

    private int myCells[][];

    public Cells(int n, int m) {
        this.myCells = new int[n+2][m+2];
    }



    public void setVisited(int x, int y)
    {
        myCells[x][y] = 1;
    }

    public boolean isVisited(int x, int y)
    {

        if(myCells[x][y] == 1)
            return true;

        return false;
    }
}
