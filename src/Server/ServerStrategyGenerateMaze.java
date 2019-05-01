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
        try
        {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Object mazeSizes = fromClient.readObject();

            if(mazeSizes instanceof int[])
            {
                int[] clientMazeSizes = (int[]) mazeSizes;
                MyMazeGenerator genMaze = new MyMazeGenerator();
                MyCompressorOutputStream compMaze = new MyCompressorOutputStream(toClient);
                Maze myGeneratedMaze = genMaze.generate(clientMazeSizes[0],clientMazeSizes[1]);
                myGeneratedMaze.print();
                byte[] test = myGeneratedMaze.toByteArray();
                compMaze.write(test);
                compMaze.flush();
            }
        }

        catch (Exception e) {}
    }
}
