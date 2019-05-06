package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        System.out.println("Server: Started Server Strategy");
        try
        {
            //TODO: replace ObjectOutputStream to MyCompressorOutputStream
            System.out.println("Server: Setting InputStream");
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            System.out.println("Server: Setting OutputStream");
            ObjectOutputStream toClient = new ObjectOutputStream(new MyCompressorOutputStream(outToClient));
//            MyCompressorOutputStream toClient = new MyCompressorOutputStream(outToClient);
            System.out.println("Server: Reading New Object");
            Object mazeSizes = fromClient.readObject();

            System.out.println("Server: Reading Array of maze params");
            if(mazeSizes instanceof int[])
            {
                int[] clientMazeSizes = (int[]) mazeSizes;
                MyMazeGenerator genMaze = new MyMazeGenerator();
//                ObjectOutputStream compMaze = new ObjectOutputStream(toClient);
                MyCompressorOutputStream compMaze = new MyCompressorOutputStream(toClient);
                Maze myGeneratedMaze = genMaze.generate(clientMazeSizes[0],clientMazeSizes[1]);
                myGeneratedMaze.print();
                byte[] test = myGeneratedMaze.toByteArray();
                compMaze.write(test);
                compMaze.flush();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
