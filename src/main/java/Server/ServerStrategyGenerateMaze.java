package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    private static final Logger LOG = LogManager.getLogger(); //Log4j2
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
//            System.out.println("Server: mazeGeneratingStrategy started");
            LOG.info("Started Maze Generator Server");
            String generatorType = Configurations.getInstance().getProperty("GeneratorType");
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            //read from client the maze dimensions
            Object mazeSizes = fromClient.readObject();
            AMazeGenerator genMaze = null;
            String genAlgoName = "";
            //check array type
//            System.out.println("Server: mazeGeneratingStrategy started");
            if(mazeSizes instanceof int[])
            {
                int[] clientMazeSizes = (int[]) mazeSizes;
                //create new maze
                if (generatorType.toLowerCase().contains("empty"))
                {
                    genMaze = new EmptyMazeGenerator();
                    genAlgoName = "EmptyMazeGenerator";
                }
                else if(generatorType.toLowerCase().contains("simple"))
                {
                    genMaze = new SimpleMazeGenerator();
                    genAlgoName = "SimpleMazeGenerator";
                }
                else
                {
                    genMaze = new MyMazeGenerator();
                    genAlgoName = "MyMazeGenerator";
                    //throw new Exception("Wrong generator type");
                }
                LOG.info("Generating Maze Using " + genAlgoName);
                LOG.info("Generating Maze of Sizes [" + clientMazeSizes[0] +"][" + clientMazeSizes[1]+"]");
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
            LOG.error(e.getMessage());
        }
    }
}
