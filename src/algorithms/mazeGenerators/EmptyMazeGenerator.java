package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int cols) {
        int[][] noWalls = new int[rows][cols];
        Maze newMaze = new Maze(noWalls);
        return newMaze;
    }
}
