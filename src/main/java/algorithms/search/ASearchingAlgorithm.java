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

    /**
     *Return the solution for our problem
     * @param state
     * @return Solution
     */
    protected Solution getSolution(AState state)
    {
        //list of solution's states
        ArrayList<AState> solutionStates = new ArrayList<>();
        //check null case
        if(state == null)
            return new Solution(solutionStates);
//        AState p = state.getParent();

        //stack of the path
        Stack<AState> pathToSolution = new Stack<>();
        //will push from the End to the Start in order to receive the solution path
        while(!(state.getParent() == null))
        {
            pathToSolution.add(state);
            state = state.getParent();
        }
        //Add the Start Position
        pathToSolution.add(state);
        int stackSize = pathToSolution.size();
        //will add the path's states from the stack to the solution's list
        for (int i = 0; i < stackSize; i++)
        {
            solutionStates.add(pathToSolution.pop());
        }

        return new Solution(solutionStates);
    }

    /**
     * @return number Of nodes evaluated
     */
    public int getNumberOfNodesEvaluated() {
        return this.numOfVisitedNodes;
    }

}
