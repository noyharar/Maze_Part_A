package algorithms.mazeGenerators;


import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

/*
    /**
     * init the maze Array with 5 in order to sign unvisited cells
     *//*
    private void init(Maze myMaze, int num) {
        for (int i = 0; i < myMaze.getHeight(); i++) {
            for (int j = 0; j < myMaze.getWidth(); j++) {
                myMaze.getMazeArray()[i][j]= 5;
            }
        }
    }
    */
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


    /**
     * @param height
     * @param width
     * @return random maze on size height x width
     */
    @Override
    public Maze generate(int height, int width) {
//        height = rows;
//        width = cols;
       // remain = height * width;
       // mazeArray = new int[getHeight][width];

        this.maze = new Maze(height,width);
        //init the new maze with 5
        this.maze.init(5);

        //myMaze = new Maze(mazeArray);
        Position sPos,ePos;

        //random start and end position
        sPos = randomPos(height,width,null);
        ePos = randomPos(height,width,null);

        //will random in case that start position equals to end position
        while ((height != 1 && width != 1) && (sPos.getRowIndex() == ePos.getRowIndex() || sPos.getColumnIndex() == ePos.getColumnIndex()))
        {
            ePos = randomPos(height,width,null);
        }

        //will set the start/end position and will init the start/end with zero
        this.maze.setStartPosition(sPos);
        this.maze.getMazeArray()[sPos.getRowIndex()][sPos.getColumnIndex()] = 0;

        this.maze.setGoalPosition(ePos);
        this.maze.getMazeArray()[ePos.getRowIndex()][ePos.getColumnIndex()] = 0;
        //will make the path between the start to end
        setCourse(this.maze,sPos,ePos);
        //after we have path between the start position to the end will randomize the other cells's value
        randomizeWalls(this.maze);


        return this.maze;


    }

    /**
     * This Function radomizes all the walls in the maze
     * that are not part of the valid course
     */
    private void randomizeWalls(Maze myMaze)
    {
        Random genNum = new Random();
        for (int i = 0; i < myMaze.getHeight(); i++) {
            for (int j = 0; j < myMaze.getWidth(); j++) {
                //if it's 5, we never visited in this position
                if(myMaze.getMazeArray()[i][j]== 5)
                {
                    //will random with 0 or 1
                    myMaze.getMazeArray()[i][j]= (genNum.nextInt(2));
                }
            }
        }
    }

    /**
     * This function is Creating the valid course of the main to ensure that
     * there is at least one valid course.
     * @param sPos - Start Postion of the maze
     * @param ePos - End Postion of the maze
     */
    private void setCourse(Maze myMaze, Position sPos, Position ePos)
    {
        int nextStep, lastRowsDistance = Math.abs(ePos.getRowIndex() - sPos.getRowIndex()), lastColDistance = Math.abs(ePos.getColumnIndex() - sPos.getColumnIndex());
        Position next = new Position(sPos);
        Random genNum = new Random();
        int nextRow, nextCol;

        while (!next.equals(ePos))
        {
            //will choose random side
            nextStep = genNum.nextInt(4);
            switch (nextStep)
            {
            //for every case will check the distance and set as 0 if we can
                case 0:
                    //Up
                    nextRow = next.getRowIndex()-1;
                    nextCol = next.getColumnIndex();
                    if(nextRow >= 0 && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setRow(nextRow);
                        myMaze.getMazeArray()[nextRow][nextCol] = 0;
                        lastRowsDistance = Math.abs(nextRow - ePos.getRowIndex());
                    }
                    break;
                case 1:
                    //Right
                    nextRow = next.getRowIndex();
                    nextCol = next.getColumnIndex()+1;
                    if(nextCol < myMaze.getWidth() && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setCol(nextCol);
                        myMaze.getMazeArray()[nextRow][nextCol]= 0;
                        lastColDistance = Math.abs(nextCol - ePos.getColumnIndex());
                    }
                    break;
                case 2:
                    //Down
                    nextRow = next.getRowIndex()+1;
                    nextCol = next.getColumnIndex();
                    if(nextRow < myMaze.getHeight() && isAbsPossible(ePos, lastRowsDistance, lastColDistance, nextRow, nextCol))
                    {
                        next.setRow(nextRow);
                        myMaze.getMazeArray()[nextRow][nextCol]= 0;
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
                        myMaze.getMazeArray()[nextRow][nextCol]= 0;
                        lastColDistance = Math.abs(nextCol - ePos.getColumnIndex());
                    }
                    break;
            }

        }
    }

    /**
     * @param goalPos
     * @param rowDistance
     * @param colDistance
     * @param nextRow
     * @param nextCol
     * @return
     */
    private boolean isAbsPossible(Position goalPos, int rowDistance, int colDistance, int nextRow, int nextCol) {
        return Math.abs(nextRow - goalPos.getRowIndex()) <= rowDistance &&
                Math.abs(nextCol - goalPos.getColumnIndex()) <= colDistance;
    }


}
