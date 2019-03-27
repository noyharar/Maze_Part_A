package algorithms.mazeGenerators;


import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverAnonymous;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

    private int height;
    private int width;
    private int[][] mazeArray;
    private Maze myMaze;
    private int remain;
    private final int extraSize = 2;


    private void init() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = 5;
            }
        }

//         Init the first Row and the last Row as visited
//        for (int i = 0; i < height; i++) {
//            mazeArray[i][0] = 1;
//            mazeArray[i][height - 1] = 1;
//        }
//
//        // Init the first Col and the last Col as visited
//        for (int j = 0; j < width; j++) {
//            mazeArray[0][j] = 1;
//            mazeArray[width - 1][j] = 1;
//        }
    }



    @Override
    public Maze generate(int rows, int cols) {
        height = rows;
        width = cols;
        remain = height * width;
        mazeArray = new int[height][width];
        init();


        myMaze = new Maze(mazeArray);
        Position sPos,ePos;

        sPos = randomPos(height,width);
        ePos = randomPos(height,width);

        while (sPos.getRowIndex() == ePos.getRowIndex() || sPos.getColumnIndex() == ePos.getColumnIndex())
        {
            ePos = randomPos(height,width);
        }


        myMaze.setStartPosition(sPos);
        myMaze.maze[sPos.getRowIndex()][sPos.getColumnIndex()] = 0;

        myMaze.setGoalPosition(ePos);
        myMaze.maze[ePos.getRowIndex()][ePos.getColumnIndex()] = 0;

        setCourse(sPos,ePos);

        randomizeWalls();

//        mazeRecursiveCreation(, );

        return myMaze;


    }

    private void randomizeWalls()
    {
        Random genNum = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(myMaze.maze[i][j] == 5)
                {
                    myMaze.maze[i][j] = genNum.nextInt(2);
                }
            }
        }
    }

    private void setCourse(Position sPos, Position ePos)
    {
        int nextStep, lastRowsDistance = Math.abs(ePos.getRowIndex() - sPos.getRowIndex()), lastColDistance = Math.abs(ePos.getColumnIndex() - sPos.getColumnIndex());
        Position next = new Position(sPos);
        Random genNum = new Random();
        int nextRow, nextCol;

        while (!next.isEqual(ePos))
        {
            nextStep = genNum.nextInt(4);
            switch (nextStep)
            {
                case 0:
                    //Up
                    nextRow = next.getRowIndex()-1;
                    nextCol = next.getColumnIndex();
                    if(nextRow >= 0 && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setRow(nextRow);
                        myMaze.maze[nextRow][nextCol]=0;
                        lastRowsDistance = Math.abs(nextRow - ePos.getRowIndex());
                    }
                    break;
                case 1:
                    //Right
                    nextRow = next.getRowIndex();
                    nextCol = next.getColumnIndex()+1;
                    if(nextCol < width && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setCol(nextCol);
                        myMaze.maze[nextRow][nextCol]=0;
                        lastColDistance = Math.abs(nextCol - ePos.getColumnIndex());
                    }
                    break;
                case 2:
                    //Down
                    nextRow = next.getRowIndex()+1;
                    nextCol = next.getColumnIndex();
                    if(nextRow < height && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setRow(nextRow);
                        myMaze.maze[nextRow][nextCol]=0;
                        lastRowsDistance = Math.abs(nextRow - ePos.getRowIndex());
                    }
                    break;
                case 3:
                    //Left
                    nextRow = next.getRowIndex();
                    nextCol = next.getColumnIndex()-1;
                    if(nextCol >= 0 && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setCol(nextCol);
                        myMaze.maze[nextRow][nextCol]=0;
                        lastColDistance = Math.abs(nextCol - ePos.getColumnIndex());
                    }
                    break;
            }

        }
    }

    private boolean isAbsPossible(Position goalPos, int rowDistance, int colDistance, int nextRow, int nextCol) {
        return Math.abs(nextRow - goalPos.getRowIndex()) <= rowDistance &&
                Math.abs(nextCol - goalPos.getColumnIndex()) <= colDistance;
    }


   /* private void mazeRecursiveCreation(int row, int col) {
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


    }*/
}
