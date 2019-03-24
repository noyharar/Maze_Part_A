package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

    private int height;
    private int width;
    private int[][] mazeArray;
    private Maze myMaze;
    private int remain;
    private final int extraSize = 2;
    private Random genNum = new Random();


    private void init() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = 5;
            }
        }

//         Init the first Row and the last Row as visited
        for (int i = 0; i < height; i++) {
            mazeArray[i][0] = 1;
            mazeArray[i][height - 1] = 1;
        }

        // Init the first Col and the last Col as visited
        for (int j = 0; j < width; j++) {
            mazeArray[0][j] = 1;
            mazeArray[width - 1][j] = 1;
        }
    }



    @Override
    public Maze generate(int rows, int cols) {
        height = rows + extraSize;
        width = cols + extraSize;
        remain = (height - extraSize) * (width - extraSize);
        mazeArray = new int[height][width];
        init();


        myMaze = new Maze(mazeArray);


        int sPosX = 0, sPosY = 0;
        while (sPosX <= 0 || sPosX > (height - extraSize)) {
            sPosX = genNum.nextInt(height) + 1;
        }

        while (sPosY <= 0 || sPosY > (width - extraSize)) {
            sPosY = genNum.nextInt(width) + 1;
        }

        Position startPostion = new Position(sPosX, sPosY);
        myMaze.setStartPosition(startPostion);
        myMaze.maze[sPosX][sPosY] = 0;

        mazeRecursiveCreation(sPosX, sPosY);

        return myMaze;


    }

    private void mazeRecursiveCreation(int row, int col) {
        remain--;
        if (remain <= 1) {
            Position endPostion = new Position(row, col);
            myMaze.setGoalPosition(endPostion);
            return;
        }

//        if (myMaze.maze[row][col] == 1)
//            return;

        myMaze.maze[row][col] = 0;
//        remain--;
        while (myMaze.maze[row][col - 1] == 5 || myMaze.maze[row + 1][col] == 5 || myMaze.maze[row][col + 1] == 5 || myMaze.maze[row - 1][col] == 5) {
            while (true) {
                int r1 = genNum.nextInt(4);
                if (r1 == 0 && myMaze.maze[row][col - 1] == 5) {
//                    myMaze.maze[row][col - 1] = 0;
                    myMaze.maze[row][col+1] = 1;
                    mazeRecursiveCreation(row, col - 1);
                    remain--;
                    break;
                } else if (r1 == 1 && myMaze.maze[row + 1][col] == 5) {
//                    myMaze.maze[row + 1][col] = 0;
                    myMaze.maze[row-1][col] = 1;
                    mazeRecursiveCreation(row + 1, col);
                    remain--;
                    break;
                } else if (r1 == 2 && myMaze.maze[row][col + 1] == 5) {
//                    myMaze.maze[row][col + 1] = 0;
                    myMaze.maze[row][col-1] = 1;
                    mazeRecursiveCreation(row, col + 1);
                    remain--;
                    break;
                } else if (r1 == 3 && myMaze.maze[row - 1][col] == 5) {
//                    myMaze.maze[row - 1][col] = 0;
                    myMaze.maze[row+1][col] = 1;
                    mazeRecursiveCreation(row - 1, col);
                    remain--;
                    break;
                }


            }
        }


    }
}
