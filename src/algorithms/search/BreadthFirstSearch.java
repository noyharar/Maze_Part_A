package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm
{
    protected Queue<AState> stateQueue;

    public BreadthFirstSearch() {
        stateQueue = new LinkedList<>();
    }

    /**
     *solve problem with breadth first search
     * @param  s
     * @return solution
     */
    @Override
    public Solution solve(ISearchable s)
    {
        //check null case
        if(s == null)
            return new Solution(new ArrayList<>());
        numOfVisitedNodes = 0;
        visited.clear();
        stateQueue.clear();

        stateQueue.add(s.getStartState());
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


    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
