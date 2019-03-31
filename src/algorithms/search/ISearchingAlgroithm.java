package algorithms.search;

public interface ISearchingAlgroithm
{
    Solution solve(ISearchable s);
    int getNumOfVisited();
    String getName();

}
