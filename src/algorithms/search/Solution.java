package algorithms.search;

import java.util.ArrayList;

public class Solution
{
    private ArrayList<AState> allStatesInSolution;

    public Solution(ArrayList<AState> allStatesInSolution) {
        this.allStatesInSolution = allStatesInSolution;
    }


    public ArrayList<AState> getSolutionPath() {
        return this.allStatesInSolution;
    }
}
