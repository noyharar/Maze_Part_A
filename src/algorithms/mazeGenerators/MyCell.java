package algorithms.mazeGenerators;

public class MyCell extends ACell{

    private boolean wallDown,wallLeft;

    public boolean getWallDown() {
        return wallDown;
    }

    public void setWallDown(boolean wallDown) {
        this.wallDown = wallDown;
    }


    public boolean getWallLeft() {
        return wallLeft;
    }

    public void setWallLeft(boolean wallLeft) {
        this.wallLeft = wallLeft;
    }

    @Override
    public String toString() {
        String Cell = "";
        if(wallLeft)
            Cell += "|";
        else
            Cell += " ";
        if(wallDown)
            Cell += "_";
        else
            Cell += " ";

        return Cell;
    }
}
