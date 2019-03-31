package algorithms.search;

import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgroithm
{
    protected HashSet<AState> visited;
    protected int numOfVisitedNodes;

    public ASearchingAlgorithm() {
        this.visited = visited;
        this.numOfVisitedNodes = 0;
    }

    protected Solution getSolution(AState state)
    {
        ArrayList<AState> solutionStates = new ArrayList<>();
        if(state == null)
            return new Solution(solutionStates);
        AState p = state.getParent();

        Stack<AState> pathToSolution = new Stack<>();
        while(!(p.getParent() == null))
        {
            pathToSolution.add(p);
            p = p.getParent();
        }

        Iterator<AState> nextState = pathToSolution.iterator();
        while(nextState.hasNext())
        {
            solutionStates.add((AState)nextState);
            nextState.next();
        }

        return new Solution(solutionStates);
    }


    public int getNumOfVisited() {
        return this.numOfVisitedNodes;
    }

}
