//package test;
//import IO.MyCompressorOutputStream;
//import IO.MyDecompressorInputStream;
//import algorithms.mazeGenerators.AMazeGenerator;
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.MyMazeGenerator;
//import java.io.*;
//import java.util.Arrays;
//
//public class RunCompressDecompressMaze {
//    public static void main(String[] args) {
//        String mazeFileName = "savedMaze.maze";
//        AMazeGenerator mazeGenerator = new MyMazeGenerator();
//        Maze maze = mazeGenerator.generate(10000, 10000); //Generate new maze
//        System.out.println("Maze Generated");
//        try {
//// save maze to a file
//            System.out.println("Start compressing Maze");
//            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
//            out.write(maze.toByteArray());
//            System.out.println("Finished writing Maze to file");
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte savedMazeBytes[] = new byte[0];
//        try {
////read maze from file
//            System.out.println("Start Decompressing Maze");
//           InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
//            savedMazeBytes = new byte[maze.toByteArray().length];
//            in.read(savedMazeBytes);
//            System.out.println("Finished decompressing");
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Maze loadedMaze = new Maze(savedMazeBytes);
//        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
//        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
////maze should be equal to loadedMaze
//    }
//
//}