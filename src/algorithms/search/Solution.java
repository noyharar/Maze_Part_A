package algorithms.search;

import java.util.ArrayList;

public class Solution
{
    private ArrayList<AState> allStatesInSolution;

    public Solution(ArrayList<AState> allStatesInSolution) {
        this.allStatesInSolution = allStatesInSolution;
    }


    /**
     * @return The path's solution
     */
    public ArrayList<AState> getSolutionPath() {
        return this.allStatesInSolution;
    }
}
