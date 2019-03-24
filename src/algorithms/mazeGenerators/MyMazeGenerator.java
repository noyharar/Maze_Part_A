//package algorithms.mazeGenerators;
//import java.util.Random;
//
//
//public class MyMazeGenerator extends AMazeGenerator {
//
//    private int visited[][];
//    private int up[][];
//    private int down[][];
//    private int right[][];
//    private int left[][];
//    private int mazeArray[][];
//    private int height;
//    private int width;
//    private Maze myMaze;
//
//
//
//    public MyMazeGenerator(int n, int m) {
////        visited = new int[n+2][m+2];
////        up = new int[n+2][m+2];
////        down = new int[n+2][m+2];
////        right = new int[n+2][m+2];
////        left = new int[n+2][m+2];
//        height = n+2;
//        width = m+2;
//        init();
//        myMaze = new Maze(mazeArray);
//        myMaze = generate();
//    }
//
//    private void init()
//    {
//        // Init the first Row and the last Row as visited
//        for (int i = 0; i < height; i++)
//        {
//            mazeArray[i][0] = 1;
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
//
//
//    }
//
//    private void generate() {
//        Random startPosition = new Random();
//        int sPostionX,sPostionY;
//        sPostionX = startPosition.nextInt(height);
//        sPostionY = startPosition.nextInt(width);
//        Position myStartPostion = new Position(sPostionX,sPostionY);
//        myMaze.setStartPosition(myStartPostion);
//
//    }
//    @Override
//    public Maze generate(int rows, int cols) {
//
//
//
//
//
//        return myMaze;
//    }
//}
