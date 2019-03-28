package algorithms.mazeGenerators;

import java.util.ArrayList;

public class Maze {

    public ACell[][] maze;
    private Position startPosition;
    private Position goalPosition;

    public Maze(ACell[][] maze) {
        this.maze = maze;
    }

    public Maze(int rows, int cols) {
        this.maze = new ACell[rows][cols];
    }

    //TODO: Need to change the maze[][] to int[][] and not ACell[][] and the print should be {0,1}
    public void print() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
//                if (startPosition.isEqual(row, col)) {
//                    System.out.print("S ");
//                } else if (goalPosition.isEqual(row, col)) {
//                    System.out.print("E ");
//                } else {
//                    System.out.print(maze[row][col] + " ");
//                }
                System.out.print(maze[row][col]);
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

