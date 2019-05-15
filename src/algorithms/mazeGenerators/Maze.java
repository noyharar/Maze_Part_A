package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Maze implements Serializable {

    private int[][] mazeArray;
    private Position startPosition;
    private Position goalPosition;
    private int height;
    private int width;
    private final int BYTE_SIZE=127;

    public Maze(){ }

    public  Maze(int height, int width){
        this.mazeArray = new int[height][width];
        this.height = height;
        this.width = width;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;

        if(height != maze.height || width != maze.width) return false;

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                if(mazeArray[i][j] != maze.mazeArray[i][j]) {
                    return false;
                }
            }
        }
        return startPosition != null && startPosition.equals(maze.startPosition) &&
                goalPosition != null && goalPosition.equals(maze.goalPosition);}

    @Override
    public int hashCode() {
        int result = Objects.hash(startPosition, goalPosition, height, width, BYTE_SIZE);
        result = 31 * result + Arrays.hashCode(mazeArray);
        return result;
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
        String mazeValues = "";
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (startPosition.isEqual(row, col)) {
                    mazeValues += "S ";
                } else if (goalPosition.isEqual(row, col)) {
                    mazeValues += "E ";

                } else {
                    mazeValues += mazeArray[row][col] + " ";
                }
                //      System.out.print(maze[row][col]);
            }
            mazeValues += "\n";
        }

        System.out.print(mazeValues);
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

    public Maze(byte[] b){
        int[] dataArray = findSizeOfMazeData(0,b);
        int sRow,sCol,gRow,gCol;

        sRow = dataArray[0];
        dataArray = findSizeOfMazeData(dataArray[1],b);
        sCol = dataArray[0];
        startPosition = new Position(sRow,sCol);

        dataArray = findSizeOfMazeData(dataArray[1],b);
        gRow = dataArray[0];
        dataArray = findSizeOfMazeData(dataArray[1],b);
        gCol = dataArray[0];

        goalPosition = new Position(gRow,gCol);

        dataArray = findSizeOfMazeData(dataArray[1],b);
        height = dataArray[0];

        dataArray = findSizeOfMazeData(dataArray[1],b);
        width = dataArray[0];

        int index = dataArray[1];

        mazeArray = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeArray[i][j] = b[index++];
            }
        }
    }

    public int[] findSizeOfMazeData(int index, byte[] b){
        int sum = 0;
        while(b[index] != -1)
        {
            sum+= (int)b[index];
            index++;
        }
        int[] dataArray = new int[2];
        dataArray[0] = sum;
        dataArray[1] = ++index;
        return dataArray;
    }


    public byte[] toByteArray(){
        int size, SPosRowSize, SPosColSize,GPosRowSize,GPosColSize,heightSize,widthSize;
        SPosRowSize = startPosition.getRowIndex() / BYTE_SIZE + 1;
        SPosColSize = startPosition.getColumnIndex() / BYTE_SIZE + 1;
        GPosRowSize = goalPosition.getRowIndex() / BYTE_SIZE + 1;
        GPosColSize = goalPosition.getColumnIndex() / BYTE_SIZE + 1;
        heightSize = height / BYTE_SIZE + 1;
        widthSize = width / BYTE_SIZE + 1;
        size = SPosRowSize + SPosColSize + GPosRowSize + GPosColSize + heightSize + widthSize + (height*width);
        //for -1
        size = size + 6;

        byte[] byteArray = new byte[size];

        insertDataIntoByteArray(startPosition.getRowIndex(),0,SPosRowSize, byteArray);
        SPosRowSize += 1;
        SPosColSize += SPosRowSize;
        insertDataIntoByteArray(startPosition.getColumnIndex(),SPosRowSize,SPosColSize, byteArray);
        SPosColSize += 1;
        GPosRowSize += SPosColSize;
        insertDataIntoByteArray(goalPosition.getRowIndex(),SPosColSize,GPosRowSize, byteArray);
        GPosRowSize +=1;
        GPosColSize += GPosRowSize;
        insertDataIntoByteArray(goalPosition.getColumnIndex(),GPosRowSize,GPosColSize, byteArray);
        GPosColSize += 1;
        heightSize += GPosColSize;
        insertDataIntoByteArray(height,GPosColSize,heightSize, byteArray);
        heightSize +=1 ;
        widthSize += heightSize;
        insertDataIntoByteArray(width,heightSize,widthSize, byteArray);
        widthSize++;
        int index = widthSize;


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteArray[index] = (byte)mazeArray[i][j];
                ++index;
            }
        }

        return byteArray;
    }

    private void insertDataIntoByteArray(int specificMazeData,int startIndex, int sizeOfMazeData, byte[] byteArray) {
        int sRow =  specificMazeData;
        for(int i = startIndex; i < sizeOfMazeData; i++){
            if(sRow <= BYTE_SIZE) {
                byteArray[i] = (byte) sRow;
            }
            else
            {
                byteArray[i] = (byte)BYTE_SIZE;
            }
            sRow -= BYTE_SIZE;

        }
        byteArray[sizeOfMazeData] = -1;
    }

}