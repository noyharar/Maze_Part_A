package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm
{
    protected Stack<AState> stateStack;

    public DepthFirstSearch() {
        stateStack = new Stack<>();
    }

    /**
     * Solve the problem based on DFS algorithm
     * @param s
     * @return The solution of the searchable problem we are looking for
     */
    @Override
    public Solution solve(ISearchable s)
    {
        //check null case
        if(s == null)
            return new Solution(new ArrayList<>());
        //reset our variables
        visited.clear();
        numOfVisitedNodes = 0;
        stateStack.clear();
        //push the first to the queue and to the hash
        stateStack.push(s.getStartState());
        visited.add(s.getStartState());
        AState specificState = null;

        while (!stateStack.isEmpty())
        {
            specificState = stateStack.pop();
            //find the possible states for the specific state
            for (AState state :
                    s.getAllPossibleStates(specificState)) {
                //for every state from the possible statrs will check if already visited
                if (!visited.contains(state))
                {
                    //will pust to the queue and the hash
                    stateStack.push(state);
                    visited.add(state);
                    //update the num of the visited nodes
                    numOfVisitedNodes++;
                    state.setParent(specificState);
                    //if we are on the goal position will return the solution
                    if(state.equals(s.getGoalState()))
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
        return "Depth First Search";
    }
}
