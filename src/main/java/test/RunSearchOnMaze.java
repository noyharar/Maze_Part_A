package test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import java.util.ArrayList;
public class RunSearchOnMaze
{
    public static void main(String[] args)
    {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        maze.print();
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        ISearchingAlgorithm searcher = new BreadthFirstSearch();
        solveProblem(searchableMaze, searcher);
//        solveProblem(searchableMaze, searcher);
        searcher = new DepthFirstSearch();
        solveProblem(searchableMaze, searcher);
//        solveProblem(searchableMaze, searcher);
        searcher = new BestFirstSearch();
        solveProblem(searchableMaze, searcher);
//        solveProblem(searchableMaze, searcher);
    }
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher)
    {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s.%s",i,solutionPath.get(i)));
        }
    }
}
