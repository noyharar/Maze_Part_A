package algorithms.mazeGenerators;
import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator {

    protected Maze maze;

    public AMazeGenerator(){}

//    public Maze newMaze;
    public abstract Maze generate(int height, int width);

    /**
     * @param height of the mzae
     * @param width of the maze
     * @return the time it's take to build the current maze
     */
    @Override
    public long measureAlgorithmTimeMillis(int height, int width) {
        long startTime = System.currentTimeMillis();
        generate(height, width);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * This function return random position located at the maze
     * @param height
     * @param width
     * @return random position
     */
    protected Position randomPos(int height, int width, Position pos)
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
