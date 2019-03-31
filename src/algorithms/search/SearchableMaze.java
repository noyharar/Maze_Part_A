package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable
{
    Maze myMaze;


    public SearchableMaze(Maze myMaze) {
        this.myMaze = myMaze;
    }

    @Override
    public AState getStartState() {
        return new MazeState(myMaze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(myMaze.getGoalPosition());
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s)
    {
        if(!(s instanceof MazeState))
            return new ArrayList<>();
        MazeState myState = (MazeState)s;
        Position pos = myState.getPosition();
        ArrayList<AState> solution = new ArrayList<>();

        for (int row = -1; row <= 1; row++)
        {
            for (int col = -1; col < 1; col++)
            {
                try
                {
                    if(Math.abs(row)==Math.abs(col) && row != 0 && col != 0)
                    {
                        if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()+col] == 0)
                        {
                            if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()] == 0 ||
                                    myMaze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex()+col] == 0)
                            {
                                solution.add(new MazeState(new Position(pos.getRowIndex() + row, pos.getColumnIndex() + col)));
                            }
                        }
                    }
                    else if(row != 0 && col != 0)
                    {
                        if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()+col] == 0)
                        {
                            solution.add(new MazeState(new Position(pos.getRowIndex() + row, pos.getColumnIndex() + col)));
                        }
                    }
                }
                catch (Exception e){
                    //ignore ArrayIndexOutOfBound
                }

            }
        }

        return solution;

    }

}
