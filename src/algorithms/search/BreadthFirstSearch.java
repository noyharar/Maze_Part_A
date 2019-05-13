package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BreadthFirstSearch extends ASearchingAlgorithm
{
    protected Queue<AState> stateQueue;
//    Logger log = Logger.getLogger("BFS_Logger");
//    Level lvl = Level.ALL;
//    ConsoleHandler cnslHndlr = new ConsoleHandler();


    public BreadthFirstSearch() {
        stateQueue = new LinkedList<>();
//        /*********** Loggin Section ******************/
//        log.setLevel(lvl);
//        cnslHndlr.setFormatter(new SimpleFormatter());
//        log.addHandler(cnslHndlr);
//        cnslHndlr.setLevel(lvl);
//        /********************************************/
    }

    /**
     *solve problem with breadth first search
     * @param s - Searchable something
     * @return solution
     */
    @Override
    public Solution solve(ISearchable s)
    {
        // Yes it Should, obvious by Aviad...
        // Sounds not legit, best should always evaluate less nodes


        //check null case

        if(s == null || s.getStartState() == null || s.getGoalState() == null)
            return new Solution(new ArrayList<>());
        numOfVisitedNodes = 0;
        visited.clear();
        stateQueue.clear();

        stateQueue.add(s.getStartState());
//        System.out.println("Im Searcher of type: " + this.getName());
//        System.out.println(this.getName() + " Added Stated State: " + s.getStartState().toString());
        //update the num of visited states
        numOfVisitedNodes++;
        //add the start state to the visited hashSet
        visited.add(s.getStartState());
        AState specificState = null;

        while(!stateQueue.isEmpty())
        {
            //state from the queue
            specificState = stateQueue.poll();
            //if we get arrived to the end we get solution
            if (specificState.equals(s.getGoalState()))
            {
                return getSolution(specificState);
            }
            //check all the states with their possible neighbors
            for (AState state : s.getAllPossibleStates(specificState))
                {
                //if the state is not located at the hashSet = not visited
                if(!visited.contains(state))
                {
                    //add to the visited set and updated the counter
                    visited.add(state);
                    numOfVisitedNodes++;
                    //will set the parent of the current state
                    state.setParent(specificState);
                    //add the state to the queue
                    stateQueue.add(state);
//                    System.out.println("Im Searcher of type: " + this.getName());
//                    System.out.println(this.getName() + " Added another state: " + state.toString());
                    //when get arrived to the end position, will return the solution
                    if (state.equals(s.getGoalState()))
                    {
                        return getSolution(state);
                    }
                }
            }
        }
        return new Solution(new ArrayList<>());
    }

    /**
     * @return the name of the search
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
