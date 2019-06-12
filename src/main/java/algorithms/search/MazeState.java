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


    /**
     * @param obj
     * @return if the both objects are equals by the position's state
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MazeState))
            return false;
        MazeState mzstate = (MazeState)obj;
        return this.positionState.equals(mzstate.positionState);
    }

    /**
     * @return the position string
     */
    @Override
    public String toString() {
        return this.positionState.toString();
    }

    /**
     * @return position's state
     */
    public Position getPosition()
    {
        return this.positionState;
    }

    /**
     * Hash function
     * @return hash index
     */
    @Override
    public int hashCode() {
        return positionState.getRowIndex() + positionState.getColumnIndex() * this.height;
    }

    /**
     * compare between two MazeStates by the costs
     * @param o
     * @return 0,1,-1
     */
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
