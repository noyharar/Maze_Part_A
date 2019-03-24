package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

//    public Maze newMaze;
    public abstract Maze generate(int rows, int cols);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long startTime = System.currentTimeMillis();
        generate(rows, cols);
        return System.currentTimeMillis() - startTime;
    }
}
