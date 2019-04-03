package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm
{
    protected Stack<AState> stateStack;

    public DepthFirstSearch() {
        stateStack = new Stack<>();
    }

    @Override
    public Solution solve(ISearchable s)
    {
        if(s == null)
            return new Solution(new ArrayList<>());

        stateStack.push(s.getStartState());
        visited.add(s.getStartState());
        AState specificState = null;

        while (!stateStack.isEmpty())
        {
            specificState = stateStack.pop();
            for (AState state :
                    s.getAllPossibleStates(specificState)) {
                if (!visited.contains(state))
                {
                    stateStack.push(state);
                    visited.add(state);
                    numOfVisitedNodes++;
                    state.setParent(specificState);
                    if(state.equals(s.getGoalState()))
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
        return "Depth First Search";
    }
}
