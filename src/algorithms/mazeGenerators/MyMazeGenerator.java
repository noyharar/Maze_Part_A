package algorithms.mazeGenerators;
import java.util.Random;


public class MyMazeGenerator extends AMazeGenerator {

//    private int visited[][];
//    private int up[][];
//    private int down[][];
//    private int right[][];
//    private int left[][];

    private MyCell mazeArray[][];
    private int height;
    private int width;
    private MyMaze myMaze;
    private Random genNum = new Random();




    private void init()
    {
//        // Init the first Row and the last Row as visited
//        for (int i = 0; i < height; i++)
//        {
//            mazeArray[i][0].setVisited(5);
//            mazeArray[i][height-1] = 1;
//        }
//
//        // Init the first Col and the last Col as visited
//        for (int j = 0; j < width; j++)
//        {
//            mazeArray[0][j] = 1;
//            mazeArray[width-1][j] = 1;
//        }
//
//        // Set all Walls as none
//        for (int i = 1; i < height-1; i++) {
//            for (int j = 1; j < width-1; j++) {
////                up[i][j] = 0;
////                down[i][j] = 0;
////                left[i][j] = 0;
////                right[i][j] = 0;
//                mazeArray[i][j]=0;
//            }
//
//        }

        /**
         * Sets the every cells in the maze as unvisited and sets all the walls as active/exists
         */
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = new MyCell();
                mazeArray[i][j].setVisited(5);
                mazeArray[i][j].setWallDown(true);
                mazeArray[i][j].setWallLeft(true);
            }

        }

        /**
         * Sets the last Column as not Available
         */
        for (int i = 0; i < height; i++) {
            mazeArray[i][width-1].setVisited(8);
        }
        /**
         * Sets the last Rows as not Available
         */
        for (int i = 0; i < width; i++) {
            mazeArray[height-1][i].setVisited(8);
        }

    }

    private void generate() {
        Random startPosition = new Random();
        int sPostionX,sPostionY;
        sPostionX = startPosition.nextInt(height);
        sPostionY = startPosition.nextInt(width);

        Position myStartPostion = new Position(sPostionX,sPostionY);
        myMaze.setStartPosition(myStartPostion);

    }
    @Override
    public Maze generate(int rows, int cols) {
        height = rows + 1;
        width = cols + 1;
        mazeArray = new MyCell[height][width];
        init();
        myMaze = new MyMaze(mazeArray);

        Position startPos = randomPos(rows, cols),endPos;
        myMaze.setStartPosition(startPos);
//        mazeArray[startPos.getRowIndex()][startPos.getColumnIndex()].setVisited(0);
        while(startPos != null)
        {
            DFSRecursive(startPos);
            startPos = getNextPos();
        }







        return myMaze;
    }

    private Position getNextPos() {
        Position nextPos = randomPos(height-1,width-1);

        if(!anyUnvisitedLeft())
            return null;
        while(mazeArray[nextPos.getRowIndex()][nextPos.getColumnIndex()].getVisited() != 5)
            nextPos = randomPos(height-1,width-1);

        return nextPos;


    }

    private void DFSRecursive(Position nextPos) {
        if(mazeArray[nextPos.getRowIndex()][nextPos.getColumnIndex()].getVisited() != 5)
        {
            return;
        }
        int nextStep;

        MyCell myself = mazeArray[nextPos.getRowIndex()][nextPos.getColumnIndex()];
        myself.setVisited(0);

        while(haveNeighbours(nextPos))
        {
            nextStep = genNum.nextInt(4);
            switch (nextStep)
            {
                case 0:
                    //Up
                    if(nextPos.getRowIndex()-1 >= 0)
                    {
                        MyCell upCell = mazeArray[nextPos.getRowIndex()-1][nextPos.getColumnIndex()];
                        if(upCell.getVisited() == 5)
                        {
                            Position upPosition = new Position(nextPos.getRowIndex()-1,nextPos.getColumnIndex());
                            upCell.setWallDown(false);
                            DFSRecursive(upPosition);
                        }
                    }
                    break;
                case 1:
                    //Right
                    if(nextPos.getColumnIndex()+1 <= width)
                    {
                        MyCell rightCell = mazeArray[nextPos.getRowIndex()][nextPos.getColumnIndex()+1];
                        if(rightCell.getVisited() == 5)
                        {
                            Position rightPosition = new Position(nextPos.getRowIndex(), nextPos.getColumnIndex()+1);
                            rightCell.setWallLeft(false);
                            DFSRecursive(rightPosition);
                        }
                    }
                    break;
                case 2:
                    //Down
                    if(nextPos.getRowIndex()+1 <= height)
                    {
                        MyCell downCell = mazeArray[nextPos.getRowIndex()+1][nextPos.getColumnIndex()];
                        if(downCell.getVisited() == 5)
                        {
                            Position downPosition = new Position(nextPos.getRowIndex()+1, nextPos.getColumnIndex());
                            myself.setWallDown(false);
                            DFSRecursive(downPosition);
                        }
                    }
                    break;
                case 3:
                    //Left
                    if (nextPos.getColumnIndex()-1 >= 0)
                    {
                        MyCell leftCell = mazeArray[nextPos.getRowIndex()][nextPos.getColumnIndex()-1];
                        if(leftCell.getVisited() == 5)
                        {
                            Position leftPosition = new Position(nextPos.getRowIndex(),nextPos.getColumnIndex()-1);
                            myself.setWallLeft(false);
                            DFSRecursive(leftPosition);
                        }
                    }
                    break;
            }
        }



    }

    private boolean haveNeighbours(Position searchPos) {

        if ((searchPos.getRowIndex() - 1 >= 0) && (mazeArray[searchPos.getRowIndex() - 1][searchPos.getColumnIndex()].getVisited() == 5))
            return true;
        if ((searchPos.getColumnIndex() - 1 >= 0) && (mazeArray[searchPos.getRowIndex()][searchPos.getColumnIndex() - 1].getVisited() == 5))
            return true;
        if (mazeArray[searchPos.getRowIndex() + 1][searchPos.getColumnIndex()].getVisited() == 5)
            return true;
        if (mazeArray[searchPos.getRowIndex()][searchPos.getColumnIndex() + 1].getVisited() == 5)
            return true;

        return false;
    }

    private boolean anyUnvisitedLeft() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(mazeArray[i][j].getVisited()==5)
                {
                    return true;
                }
            }
        }
        return false;
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
