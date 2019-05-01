package algorithms.mazeGenerators;

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


    /**
     * Generates a new array and copies the values from the old one
     * @param maze
     */
    public Maze(int[][] maze)
    {
        this.mazeArray = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                this.mazeArray[i][j] = maze[i][j];
            }
        }
        
    }

    public Maze(int[][] maze, Position startPosition, Position goalPosition){
        this(maze);
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

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public void setMazeArray(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public void init(int num) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = num;
            }
        }
    }

    /**
     * Added an else if the maze[i][j]==5
     * which means its parts of the solution
     * this value is setted in RunSearchOnMaze:printSolution
     */
    public void printColor () {
        for (int i = 0; i < this.mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[i].length; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                {//startPosition
                    System.out.print(" " + "\u001B[40m" + " ");
                }
                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                }
                else if (mazeArray[i][j] == 1)
                {
                    System.out.print(" " + "\u001B[45m" + " ");
                }
                else if (mazeArray[i][j] == 5)
                {
                    System.out.print(" " + "\u001B[43m" + " ");
                }
                else System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }

    }

    //TODO: print Solution


    public void printZeroOnes() {
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


    public void findColors() {
        for (int i = 40; i < 50; i++) {
            System.out.print("Color number " + i + ": " + "\u001B[" + i + "m ");
            System.out.println();
        }
    }
}

