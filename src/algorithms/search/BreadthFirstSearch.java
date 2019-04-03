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

    @Override
    public Solution solve(ISearchable s)
    {
        if(s == null)
            return new Solution(new ArrayList<>());
        numOfVisitedNodes = 0;
        visited.clear();
        stateQueue.clear();

        stateQueue.add(s.getStartState());
        numOfVisitedNodes++;
        visited.add(s.getStartState());
        AState specificState = null;
        while(!stateQueue.isEmpty())
        {
            specificState = stateQueue.poll();
            if (specificState.equals(s.getGoalState()))
            {
                return getSolution(specificState);
            }

            for (AState state : s.getAllPossibleStates(specificState))
            {
                if(!visited.contains(state))
                {
                    visited.add(state);
                    numOfVisitedNodes++;
                    state.setParent(specificState);
                    stateQueue.add(state);
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
