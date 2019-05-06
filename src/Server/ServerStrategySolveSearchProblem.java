package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private Map<Integer,Maze> bankSolutions = new HashMap<>();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try
        {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Object mazeSizes = fromClient.readObject();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");

            if(mazeSizes instanceof Maze)
            {
                Solution solution = null;
                Maze clientMazeToSolve = (Maze) mazeSizes;
                clientMazeToSolve.print();
                if(!bankSolutions.isEmpty()){
                    System.out.println("=====================================================================");
                    bankSolutions.get(0).print();
                }
                if(bankSolutions.containsValue(clientMazeToSolve)){
                    int id = 0;
                    //TODO: get solution from file with ID bankSolutions.getID
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
                    BestFirstSearch seaechAlgo = new BestFirstSearch();
                    SearchableMaze searchableMaze = new SearchableMaze(clientMazeToSolve);
                    solution = seaechAlgo.solve(searchableMaze);
                    //TODO: save solution to file with ID bankSolution.getID
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

        catch (Exception e) {}
    }
}
