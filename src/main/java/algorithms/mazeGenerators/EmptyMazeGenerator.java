package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    /**
     * @param height
     * @param width
     * @return maze without walls - only zeros
     */
    @Override
    public Maze generate(int height, int width) {
        int[][] noWalls = new int[height][width];
        Maze newMaze = new Maze(noWalls);
        return newMaze;
    }
}
