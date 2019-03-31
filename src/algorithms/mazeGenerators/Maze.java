package algorithms.mazeGenerators;

import java.util.ArrayList;

public class Maze {

    public int[][] mazeArray;
    private Position startPosition;
    private Position goalPosition;
    private int height;
    private int width;

    public Maze(){ }

    public  Maze(int height, int width){
        this.mazeArray = new int[height][width];
        this.height = height;
        this.width = width;
    }


    public Maze(int[][] maze){
            this.mazeArray = maze;
    }

    public Maze(int[][] maze, Position startPosition, Position goalPosition) {
        this.mazeArray = maze;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    /* public Maze(ACell[][] maze) {
            this.maze = maze;
        }

        public Maze(int rows, int cols) {
            this.maze = new ACell[rows][cols];
        }
    */



    public void init(int num) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = num;
            }
        }
    }
    public void print() {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (startPosition.isEqual(row, col)) {
                        System.out.print("S ");
                    } /*else if (goalPosition.isEqual(row, col)) {
                        System.out.print("E ");

                    } */else {
                        System.out.print(mazeArray[row][col] + " ");
                    }
              //      System.out.print(maze[row][col]);
                }
                System.out.println();
            }
        }


    public int[][] getMaze() {
        return mazeArray;
    }

    public void setMaze(int[][] maze) {
        this.mazeArray = maze;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

