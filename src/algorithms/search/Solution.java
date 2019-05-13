package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable
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
