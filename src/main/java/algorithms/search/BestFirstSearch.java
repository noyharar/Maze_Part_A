package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch
{
    public BestFirstSearch()
    {
        stateQueue = new PriorityQueue<AState>();
    }

    /**
     * @return the name of the search
     */
    @Override
    public String getName() {
        return "Best First Search";
    }
}
