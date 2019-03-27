package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int cols) {
        ACell[][] noWalls = new ACell[rows][cols];
        Maze newMaze = new Maze(noWalls);
        return newMaze;
    }
}
