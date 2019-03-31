package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int height, int width) {
        int[][] noWalls = new int[height][width];
        Maze newMaze = new Maze(noWalls);
        return newMaze;
    }
}
