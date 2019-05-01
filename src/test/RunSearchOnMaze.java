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
        Maze maze = mg.generate(10, 10);
//        maze.findColors();
//        maze.printColor();
//        maze.printZeroOnes();
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        ISearchingAlgorithm searcher = new BreadthFirstSearch();
        solveProblem(searchableMaze, searcher);
//        solveProblem(searchableMaze, searcher);
//        searcher = new DepthFirstSearch();
//        solveProblem(searchableMaze, searcher);
//        solveProblem(searchableMaze, searcher);
        System.out.println();
//        maze.printColor();
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
        System.out.println("Solution path of :" + searcher.getName());
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        printSolution(solutionPath,domain);
    }

    /**
     * prints All Types of Solutions
     * @param solutionPath
     */
    private static void printRegularSolution(ArrayList<AState> solutionPath) {
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s.%s",i,solutionPath.get(i)));
        }
    }

    /**
     * Only Prints the Colored Solution of a maze
     * @param solutionPath
     * @param dom
     */
    private static void printSolution(ArrayList<AState> solutionPath,ISearchable dom) {
        if(!(dom instanceof SearchableMaze))
        {
            printRegularSolution(solutionPath);
            return;
        }
        SearchableMaze mySearchableMaze = (SearchableMaze)dom;
        Maze myMaze = mySearchableMaze.getMyMaze();
        Maze printedMaze = new Maze(myMaze.getMazeArray(),myMaze.getStartPosition(),myMaze.getGoalPosition());

        for (AState statePos : solutionPath)
        {
            MazeState myState = (MazeState)statePos;
            printedMaze.getMazeArray()[myState.getPosition().getRowIndex()][myState.getPosition().getColumnIndex()] = 5;
        }

        printedMaze.printColor();
    }
}
