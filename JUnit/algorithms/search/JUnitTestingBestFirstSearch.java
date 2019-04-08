package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import java.time.chrono.IsoChronology;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {

    BestFirstSearch best;
    BreadthFirstSearch anotherBest;
    SearchableMaze mazeForSearch;
    MyMazeGenerator mazeGenerator;
    Maze myMaze;



    public JUnitTestingBestFirstSearch() {
        best = new BestFirstSearch();
        anotherBest = new BestFirstSearch();
        mazeGenerator = new MyMazeGenerator();

    }

    @Test
    void getName() {
        assertEquals("Best First Search", best.getName());
    }

    @Test
    void testSolve2SameSearches() {
        Solution emptyMaze = best.solve((ISearchable)null);
        assertEquals(emptyMaze.getSolutionPath().size(),0);
        //TODO: check 1 and 2
        for (int i = 1; i <= 1000; i++) {
            myMaze = mazeGenerator.generate(i,i);
//            myMaze.print();
            mazeForSearch = new SearchableMaze(myMaze);
            Solution bestSol = best.solve(mazeForSearch);
            Solution anotherBestSol = anotherBest.solve(mazeForSearch);
            assertEquals(bestSol.getSolutionPath().size(),anotherBestSol.getSolutionPath().size());

        }
//        myMaze = mazeGenerator.generate(4,4);
//        mazeForSearch = new SearchableMaze(myMaze);
//        Solution bestSol = best.solve(mazeForSearch);
//        Solution anotherBestSol = anotherBest.solve(mazeForSearch);
    }

}