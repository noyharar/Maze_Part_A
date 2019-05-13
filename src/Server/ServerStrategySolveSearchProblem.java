package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private Map<Integer,Maze> bankSolutions = new HashMap<>();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
            ASearchingAlgorithm seaechAlgo = null;
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Object mazeSizes = fromClient.readObject();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            String solverType = Configurations.getInstance().getProperty("SolverType");




            if(mazeSizes instanceof Maze)
            {
                Solution solution = null;
                Maze clientMazeToSolve = (Maze) mazeSizes;
                clientMazeToSolve.print();
//                if(!bankSolutions.isEmpty()){
//                    System.out.println("=====================================================================");
////                    bankSolutions.get(0).print();
//                }
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
                    if (readSolution instanceof Solution)
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
                        throw new Exception("Wrong solver type in configurations file");
                    }

                    SearchableMaze searchableMaze = new SearchableMaze(clientMazeToSolve);
                    solution = seaechAlgo.solve(searchableMaze);
                    File tempDir = new File(tempDirectoryPath);
                    if(!tempDir.exists())
                    {
                        tempDir.mkdir();
                    }
                    File solFile = new File(tempDir.getAbsolutePath() + "\\" + bankSolutions.size());
                    if(!solFile.exists())
                    {
                        solFile.createNewFile();
                    }
                    ObjectOutputStream toSolutionFile = new ObjectOutputStream(new FileOutputStream(solFile));
                    toSolutionFile.writeObject(solution);
                    toSolutionFile.close();
                    bankSolutions.put(bankSolutions.size(),clientMazeToSolve);

                }
                toClient.flush();
                toClient.writeObject(solution);
                toClient.flush();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
