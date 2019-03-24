package algorithms.mazeGenerators;

public class Maze {

    public int[][] maze;
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
                    System.out.print("S ");
                } else if (goalPosition.isEqual(row, col)) {
                    System.out.print("E ");
                } else {
                    System.out.print(maze[row][col] + " ");
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

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }


}

