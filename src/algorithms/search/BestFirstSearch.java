package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch
{
    public BestFirstSearch()
    {
        stateQueue = new PriorityQueue<AState>();
    }

    @Override
    public String getName() {
        return "Best First Search";
    }
}
