package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState
{

    private Position positionState;

    public MazeState(Position positionState) {
        this.positionState = positionState;
        this.cost = 0D;
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
}
