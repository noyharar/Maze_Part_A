package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.util.locale.provider.LocaleServiceProviderPool;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private Map<Integer,Maze> bankSolutions;
    private String tempDirectoryPath;
    private String solverType;
    private File solutionDir;
    private final Object lockBank = new Object();
    private final Object lockLoadFiles = new Object();

    private static final Logger LOG = LogManager.getLogger(); //Log4j2

    public ServerStrategySolveSearchProblem() {
        this.bankSolutions = new HashMap<>();
        this.tempDirectoryPath = System.getProperty("java.io.tmpdir");
        this.solverType = Configurations.getInstance().getProperty("SolverType");
        LOG.info("Started Solving Mazes Server");


        File tempDir = new File(tempDirectoryPath);
        if(!tempDir.exists())
        {
            tempDir.mkdir();
        }
        LOG.info("Saving solution dir is " + tempDir.getPath());
        this.solutionDir = new File(tempDir.getAbsolutePath() + "\\solverStrategySolutions");
        checkIfSolutionExists(solutionDir,bankSolutions);

    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
            ASearchingAlgorithm seaechAlgo = null;
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);





            Object mazeSizes = fromClient.readObject();
            if(mazeSizes instanceof Maze)
            {
                Solution solution = null;
                Maze clientMazeToSolve = (Maze) mazeSizes;
                LOG.info("Solving Maze of Sizes [" + clientMazeToSolve.getHeight() + "][" + clientMazeToSolve.getWidth() + "]");
                if(bankSolutions.containsValue(clientMazeToSolve)){
                    int id = 0;
                    for (int i = 0; i < bankSolutions.size(); i++) {
                        if(bankSolutions.get(i).equals(clientMazeToSolve))
                        {
                            id = i;
                            break;
                        }
                    }
                    File solvedFile = new File(tempDirectoryPath + "\\" + id);
                    ObjectInputStream readSolvedSolution = new ObjectInputStream(new FileInputStream(solvedFile));
                    Object readSolution = readSolvedSolution.readObject();
                    if(!(readSolution instanceof  Solution))
                    {
                        readSolution = readSolvedSolution.readObject();
                    }
                    solution = (Solution)readSolution;

                    readSolvedSolution.close();

                }
                else
                {
                    if (solverType.toLowerCase().contains("best"))
                    {
                        seaechAlgo = new BestFirstSearch();
                    }
                    else if (solverType.toLowerCase().contains("bfs") || solverType.toLowerCase().contains("breadth"))
                    {
                        seaechAlgo = new BreadthFirstSearch();
                    }
                    else if (solverType.toLowerCase().contains("dfs") || solverType.toLowerCase().contains("Depth"))
                    {
                        seaechAlgo = new DepthFirstSearch();
                    }
                    else
                    {
                        seaechAlgo = new BestFirstSearch();
                        //throw new Exception("Wrong solver type in configurations file");
                    }

                    LOG.info("Solving Maze Using " + seaechAlgo.getName() + " Algorithm");

                    SearchableMaze searchableMaze = new SearchableMaze(clientMazeToSolve);
                    solution = seaechAlgo.solve(searchableMaze);

                    synchronized (bankSolutions)
                    {
//                        System.out.println("ServerStrategy: Enterd Sync Block");
                        File solFile = new File(solutionDir.getAbsolutePath() + "\\" + bankSolutions.size());
                        if(!solFile.exists())
                        {
                            solFile.createNewFile();
                        }
                        else
                        {
                            LOG.warn("Found Duplicate Files in wrong place");
                        }
                        ObjectOutputStream toSolutionFile = new ObjectOutputStream(new FileOutputStream(solFile));

                        ByteArrayOutputStream outByte = new ByteArrayOutputStream();
                        MyCompressorOutputStream compMaze = new MyCompressorOutputStream(outByte);
                        //send the maze to compressor
                        compMaze.flush();
                        compMaze.write(clientMazeToSolve.toByteArray());
                        compMaze.flush();


                        toSolutionFile.flush();
                        toSolutionFile.writeObject(outByte.toByteArray());
                        toSolutionFile.writeObject(solution);
                        toSolutionFile.flush();
                        toSolutionFile.close();
                        bankSolutions.put(bankSolutions.size(),clientMazeToSolve);
//                        System.out.println("ServerStrategy: Exiting Sync Block" );
                    }

                }
                toClient.flush();
                toClient.writeObject(solution);
                toClient.flush();
            }

        }

        catch (Exception e) {
            LOG.error("FileNotFound " + e.getMessage());
//            e.printStackTrace();
        }
    }

    private void checkIfSolutionExists(File solutionDir, Map<Integer,Maze> bankSolutions) {
        if(!solutionDir.exists())
        {
            solutionDir.mkdir();
        }
        else
        {
            try
            {

                File[] solutions = solutionDir.listFiles();
                if(solutions != null)
                {
                    synchronized (lockLoadFiles)
                    {
                        for (File sol :
                                solutions)
                        {
                            ObjectInputStream readSol = new ObjectInputStream(new FileInputStream(sol));
                            Object maze = readSol.readObject();
                            if(maze instanceof byte[])
                            {
//                                System.out.println("Server Strategy: Found solved Maze");
                                InputStream decomp = new MyDecompressorInputStream(new ByteArrayInputStream((byte[])maze));
                                int byteRead = 0,counter = 0, height = 0,width = 0;
                                while (counter < 4)
                                {
                                    byteRead = decomp.read();
                                    if(byteRead == 255)
                                        counter++;
                                }

                                while(counter < 6)
                                {
                                    byteRead = decomp.read();
                                    if(byteRead == 255)
                                    {
                                        counter++;
                                    }
                                    else
                                    {
                                        if(counter == 4)
                                        {
                                            height += byteRead;
                                        }
                                        else if (counter == 5)
                                        {
                                            width += byteRead;
                                        }
                                    }
                                }

                                decomp = new MyDecompressorInputStream(new ByteArrayInputStream((byte[])maze));
                                byte[] mazeArray = new byte[(height*width)+54];
                                int x = decomp.read(mazeArray);
                                Maze openedMaze = new Maze(mazeArray);
                                bankSolutions.put(Integer.parseInt(sol.getName()),openedMaze);
                            }
                            readSol.close();
                        }

                    }
                }
            }
            catch (Exception e)
            {
                LOG.error(e.getMessage());
            }
        }
    }
}
