package algorithms.mazeGenerators;
import java.util.ArrayList;


public class MyMazeGenerator extends AMazeGenerator {

    private ArrayList<Position> wallList;

    public MyMazeGenerator() { }

    /**
     * build random maze based on Prim algorithm
     * @param height
     * @param width
     * @return random maze on size height x width
     */
    @Override
    public Maze generate(int height, int width) {
        //if the input less than 4, will build default maze
        if(height < 4 || width < 4)
        {
            height = 10;
            width = 10;
//            return maze;
        }
        this.maze = new Maze(height,width);
        maze.init(1);
        //will choose random start position
        maze.setStartPosition(randomPos(height,width,null));
        Position start = maze.getStartPosition();
        maze.getMazeArray()[start.getRowIndex()][start.getColumnIndex()] = 0;
        wallList = new ArrayList<>();
        //will insert the neighbours to list of walls
        insertNeighboursToList(start);

        while (!wallList.isEmpty())
        {
            try{

                int nextIndex = (int)(Math.random() * wallList.size());
                Position currentRandom = wallList.remove(nextIndex), onTheOtherEnd;

                //the other side of our current position (not the parent - the other side)
                onTheOtherEnd = currentRandom.getOtherSide();
                if(maze.getMazeArray()[currentRandom.getRowIndex()][currentRandom.getColumnIndex()] == 1)
                {

                    if(maze.getMazeArray()[onTheOtherEnd.getRowIndex()][onTheOtherEnd.getColumnIndex()] == 1)
                    {
                        //if the current position and the other side are walls - will set to 0 in order to make a path
                        maze.getMazeArray()[currentRandom.getRowIndex()][currentRandom.getColumnIndex()] = 0;
                        maze.getMazeArray()[onTheOtherEnd.getRowIndex()][onTheOtherEnd.getColumnIndex()] = 0;
                        //set every iteration the goal position - the last one will be the end of the path
                        maze.setGoalPosition(onTheOtherEnd);
                        //will add the other side position's walls to the list
                        insertNeighboursToList(onTheOtherEnd);

                    }
                }

            }
            catch (Exception e)
            {
                //ignore NullPointerExceptions
            }
        }

        return maze;
    }

    /**
     * @param pos
     * if it's a neighbour of the position and wall(num = 1) will add to the walls's list
     */
    private void insertNeighboursToList(Position pos)
    {
        try {
            //UP
            if ((pos.getRowIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex() - 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() - 1,pos.getColumnIndex(),pos));
            //Left
            if ((pos.getColumnIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() - 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() - 1, pos));
            //Down
            if ((pos.getRowIndex() - 1 <= maze.getHeight()) && (maze.getMazeArray()[pos.getRowIndex() + 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() + 1,pos.getColumnIndex(), pos));
            //Right
            if ((pos.getColumnIndex() - 1 <= maze.getWidth()) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() + 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() + 1, pos));
        }
        catch (Exception e)
        {
            //Ignore IndexArrayOutOfBound
        }

    }

}


