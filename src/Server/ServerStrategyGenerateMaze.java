package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
            System.out.println("Server: mazeGeneratingStrategy started");
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            //read from client the maze dimensions
            Object mazeSizes = fromClient.readObject();
            //check array type
//            System.out.println("Server: mazeGeneratingStrategy started");
            if(mazeSizes instanceof int[])
            {
                int[] clientMazeSizes = (int[]) mazeSizes;
                //create new maze
                MyMazeGenerator genMaze = new MyMazeGenerator();
                Maze myGeneratedMaze = genMaze.generate(clientMazeSizes[0],clientMazeSizes[1]);

                ByteArrayOutputStream outByte = new ByteArrayOutputStream();
                MyCompressorOutputStream compMaze = new MyCompressorOutputStream(outByte);
                //send the maze to compressor
                compMaze.flush();
                compMaze.write(myGeneratedMaze.toByteArray());
                compMaze.flush();
                //helpToWrite from server to client
                toClient.flush();
                toClient.writeObject(outByte.toByteArray());
                toClient.flush();
            }
        }

        catch (Exception e) {}
    }
}
