package algorithms.search;

import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected HashSet<AState> visited;
    protected int numOfVisitedNodes;

    public ASearchingAlgorithm() {
        this.visited = new HashSet<>();
        this.numOfVisitedNodes = 0;
    }

    protected Solution getSolution(AState state)
    {
        ArrayList<AState> solutionStates = new ArrayList<>();
        if(state == null)
            return new Solution(solutionStates);
//        AState p = state.getParent();

        Stack<AState> pathToSolution = new Stack<>();

        while(!(state.getParent() == null))
        {
            pathToSolution.add(state);
            state = state.getParent();
        }
        //Add the Start Position
        pathToSolution.add(state);
        int stackSize = pathToSolution.size();
        for (int i = 0; i < stackSize; i++)
        {
            solutionStates.add(pathToSolution.pop());
        }

        return new Solution(solutionStates);
    }


    public int getNumberOfNodesEvaluated() {
        return this.numOfVisitedNodes;
    }

}
