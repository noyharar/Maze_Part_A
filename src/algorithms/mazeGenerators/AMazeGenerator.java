package algorithms.mazeGenerators;
import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator {

//    public Maze newMaze;
    public abstract Maze generate(int rows, int cols);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long startTime = System.currentTimeMillis();
        generate(rows, cols);
        return System.currentTimeMillis() - startTime;
    }

    protected Position randomPos(int height, int width)
    {
        Random genNum = new Random();
        int sPosX = -1, sPosY = -1;
        while (sPosX < 0 || sPosX > (height)) {
            sPosX = genNum.nextInt(height);
        }

        while (sPosY < 0 || sPosY > (width)) {
            sPosY = genNum.nextInt(width);
        }

        Position postion = new Position(sPosX, sPosY);
        return postion;
    }
}
