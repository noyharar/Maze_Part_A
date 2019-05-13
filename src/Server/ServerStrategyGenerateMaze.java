package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
            System.out.println("Server: mazeGeneratingStrategy started");
            String generatorType = Configurations.getInstance().getProperty("GeneratorType");
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            //read from client the maze dimensions
            Object mazeSizes = fromClient.readObject();
            AMazeGenerator genMaze = null;
            //check array type
//            System.out.println("Server: mazeGeneratingStrategy started");
            if(mazeSizes instanceof int[])
            {
                int[] clientMazeSizes = (int[]) mazeSizes;
                //create new maze
                if (generatorType.toLowerCase().contains("empty"))
                {
                    genMaze = new EmptyMazeGenerator();
                }
                else if(generatorType.toLowerCase().contains("simple"))
                {
                    genMaze = new SimpleMazeGenerator();
                }
                else if(generatorType.toLowerCase().contains("mymaze"))
                {
                    genMaze = new MyMazeGenerator();
                }
                else
                {
                    throw new Exception("Wrong generator type");
                }
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

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
