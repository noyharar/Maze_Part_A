package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState
{

    private int height;
    private Position positionState;



    public MazeState(Position positionState, Maze maze) {
        if(positionState == null)
            this.positionState = null;
        else
        {
            this.positionState = positionState;
            this.state = positionState.toString();
        }
        this.cost = 0;
        this.height = maze.getHeight();
    }

    public MazeState(Position positionState, Maze maze, int cost) {
        this.positionState = positionState;
        this.cost = cost;
        this.state = positionState.toString();
        this.height = maze.getHeight();
    }



    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MazeState))
            return false;
        MazeState mzstate = (MazeState)obj;
        return this.positionState.equals(mzstate.positionState);
    }

    @Override
    public String toString() {
        return this.positionState.toString();
    }

    public Position getPosition()
    {
        return this.positionState;
    }

    @Override
    public int hashCode() {
        return positionState.getRowIndex() + positionState.getColumnIndex() * this.height;
    }

    @Override
    public int compareTo(AState o) {
        if(o instanceof MazeState){
            MazeState other = (MazeState)o;
            if(this.cost - other.cost == 0){
                return 0;
            }
            if(this.cost - other.cost > 0){
                return 1;
            }
        }
        return -1;
    }
}
