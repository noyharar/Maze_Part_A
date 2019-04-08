package algorithms.mazeGenerators;

import java.util.Random;

public class Maze {

    private int[][] mazeArray;
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

    /**
     * @return the maze's array
     */
    public int[][] getMazeArray() {
        return mazeArray;
    }

    /**
     * set maze's array
     * @param mazeArray
     */
    public void setMazeArray(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    /**
     * @param num
     * init all the maze's array with number
     */
    public void init(int num) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = num;
            }
        }
    }

    /**
     * print the maze's array
     */
    public void print() {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (startPosition.isEqual(row, col)) {
                        System.out.print("S ");
                    } else if (goalPosition.isEqual(row, col)) {
                        System.out.print("E ");

                    } else {
                        System.out.print(mazeArray[row][col] + " ");
                    }
              //      System.out.print(maze[row][col]);
                }
                System.out.println();
            }
        }
    /**
     * This Function radomizes all the walls in the maze
     * that are not part of the valid course
     */
    public void randomizeWalls(int num)
    {
        Random genNum = new Random();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //if it's 5, we never visited in this position
                if(getMazeArray()[i][j] == num)
                {
                    //will random with 0 or 1
                     getMazeArray()[i][j]= (genNum.nextInt(2));
                }
            }
        }
    }
/*
    public int[][] getMaze() {
        return mazeArray;
    }
*/
/*
    public void setMaze(int[][] maze) {
        this.mazeArray = maze;
    }
*/

    /**
     * @return the maze's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height
     * set height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the maze's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width
     * set width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return start position of the maze
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return End position of the maze
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Set start position
     * @param startPosition
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Set goal position
     * @param goalPosition
     */
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }
}

