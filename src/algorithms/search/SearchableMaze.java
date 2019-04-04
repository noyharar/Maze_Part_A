package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable
{
    private Maze myMaze;
    private final int diagonalCost = 5;
    private final int regualrCost = 10;


    public SearchableMaze(Maze myMaze) {
        this.myMaze = myMaze;
    }

    /**
     * @return the start state
     */
    @Override
    public AState getStartState() {
        Position startPos = myMaze.getStartPosition();
        if (startPos != null)
            return new MazeState(myMaze.getStartPosition(),myMaze);
        return null;
    }


    /**
     * @return the goal state
     */
    @Override
    public AState getGoalState() {
        Position endPos = myMaze.getGoalPosition();
        if(endPos != null)
            return new MazeState(myMaze.getGoalPosition(),myMaze);
        return null;
    }


    /**
     * Check if it's neighbour
     * @param row
     * @param col
     * @param maze
     * @return if it's wall or neighbour
     */
    public boolean checkNeighbour(int row, int col, Maze maze){
        if(row >= 0 && col >= 0 && row < maze.getHeight() && col < maze.getWidth()&& myMaze.getMazeArray()[row][col] == 0){
            return true;
        }
        return false;
    }

    /**
     * Check all the possible states by the clock
     * @param s
     * @return the solution
     */
    public ArrayList<AState> getAllPossibleStates(AState s)
    {
        if(!(s instanceof MazeState))
            return new ArrayList<>();
        MazeState myState = (MazeState)s;
        Position pos = myState.getPosition();
        ArrayList<AState> solution = new ArrayList<>();
        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();
        //for every neighbour, if it's 0 will add to solution
        //Up
        if(checkNeighbour(row-1,col,myMaze)){
            addStateToSolution(pos,row-1,col,regualrCost,myMaze,s,solution);
        }
        //Cross-Up-Right
        if(checkNeighbour(row-1,col+1,myMaze)){

            if(myMaze.getMazeArray()[row-1][col] == 0 || myMaze.getMazeArray()[row][col+1] == 0)
            {
                addStateToSolution(pos,row-1,col+1,diagonalCost,myMaze,s,solution);
            }
        }
        //Right
        if(checkNeighbour(row,col+1,myMaze)){
            addStateToSolution(pos,row,col+1,regualrCost,myMaze,s,solution);
        }
        //Cross-Down-Right
        if(checkNeighbour(row+1,col+1,myMaze)){
            if(myMaze.getMazeArray()[row+1][col] == 0 || myMaze.getMazeArray()[row][col+1] == 0) {
                addStateToSolution(pos, row + 1, col + 1, diagonalCost, myMaze, s, solution);
            }
        }
        //Down
        if(checkNeighbour(row+1,col,myMaze)){
            addStateToSolution(pos,row+1,col,regualrCost,myMaze,s,solution);
        }
        //Cross-Down-Left
        if(checkNeighbour(row+1,col-1,myMaze)){
            if(myMaze.getMazeArray()[row+1][col] == 0 || myMaze.getMazeArray()[row][col-1] == 0) {
                addStateToSolution(pos, row + 1, col - 1, diagonalCost, myMaze, s, solution);
            }
            }
        //Left
        if(checkNeighbour(row,col-1,myMaze)){
            addStateToSolution(pos, row,col-1,regualrCost,myMaze,s,solution);
        }
        //Cross-Up-Left
        if(checkNeighbour(row-1,col-1,myMaze)){
            if(myMaze.getMazeArray()[row-1][col] == 0 || myMaze.getMazeArray()[row][col-1] == 0) {
                addStateToSolution(pos, row - 1, col - 1, diagonalCost, myMaze, s, solution);
            }
        }
        return solution;
    }

    /**
     * This function add MazeState to the solution list
     * @param parent
     * @param row
     * @param col
     * @param cost
     * @param myMaze
     * @param s
     * @param solution
     */
    private void addStateToSolution(Position parent, int row, int col,int cost, Maze myMaze, AState s, ArrayList<AState> solution) {
        try {
            Position currPos =  new Position(row, col, parent);
            MazeState curr = new MazeState(currPos, myMaze, cost);
//            curr.setParent(s);
            curr.cost = cost;
            solution.add(curr);
        }
        catch (Exception e){}

    }


//    @Override
//    public ArrayList<AState> getAllPossibleStates(AState s)
//    {
//        if(!(s instanceof MazeState))
//            return new ArrayList<>();
//        MazeState myState = (MazeState)s;
//        Position pos = myState.getPosition();
//        ArrayList<AState> solution = new ArrayList<>();
//
//        for (int row = -1; row <= 1; row++)
//        {
//            for (int col = -1; col <= 1; col++)
//            {
//                try
//                {
//                    if(Math.abs(row)==Math.abs(col) && row != 0 && col != 0)
//                    {
//                        if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()+col] == 0)
//                        {
//                            if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()] == 0 ||
//                                    myMaze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex()+col] == 0)
//                            {
//                                solution.add(new MazeState(new Position(pos.getRowIndex() + row, pos.getColumnIndex() + col),myMaze));
//                            }
//                        }
//                    }
//                    else if(!(row == 0 && col == 0))
//                    {
//                        if(myMaze.getMazeArray()[pos.getRowIndex()+row][pos.getColumnIndex()+col] == 0)
//                        {
//                            solution.add(new MazeState(new Position(pos.getRowIndex() + row, pos.getColumnIndex() + col),myMaze));
//                        }
//                    }
//                }
//                catch (Exception e){
//                    //ignore ArrayIndexOutOfBound
//                }
//
//            }
//        }
//
//        return solution;
//
//    }

}
