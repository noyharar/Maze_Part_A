package algorithms.mazeGenerators;

public class Maze {

    private int[][] maze;
    private Position startPosition;
    private Position goalPosition;

    public Maze(int[][] maze) {
        this.maze = maze;
    }

    public Maze(int rows, int cols) {
        this.maze = new int[rows][cols];
    }

    public void print() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (startPosition.isEqual(row, col)) {
                    System.out.println("S ");
                } else if (goalPosition.isEqual(row, col)) {
                    System.out.println("E ");
                } else {
                    System.out.println(maze[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }
}

