package algorithms.mazeGenerators;
import java.util.ArrayList;


public class MyMazeGenerator extends AMazeGenerator {

    private ArrayList<Position> wallList;

    public MyMazeGenerator() { }

    /**
     * build random maze based on Prim algorithm
     * @param height the height of the maze
     * @param width the width of the maze
     * @return random maze on size height x width
     */
    @Override
    public Maze generate(int height, int width) {
        //if the input less than 4, will build default maze
        if(height <= 1 && width <= 1)
        {
            height = 10;
            width = 10;
//            return maze;
        }

        this.maze = new Maze(height,width);
        maze.init(1);
        //will choose random start position
        maze.setStartPosition(randomPos(height,width,null));
//        maze.setStartPosition(new Position(0,0));
        Position start = maze.getStartPosition();
        maze.getMazeArray()[start.getRowIndex()][start.getColumnIndex()] = 0;
        wallList = new ArrayList<>();
        //will insert the neighbours to list of walls
        insertNeighboursToList(start);

        while (!wallList.isEmpty())
        {
            try{

                int nextIndex = (int)(Math.random() * wallList.size());
                Position currentRandom = wallList.remove(nextIndex);
                Position onTheOtherEnd;

                //the other side of our current position (not the parent - the other side)
                onTheOtherEnd = currentRandom.getOtherSide(maze.getHeight(),maze.getWidth());
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
        if(maze.getGoalPosition() == null){
            Position goalPos = checkWhenNoGoalPosition(start,1);
           if(goalPos != null){
               maze.setGoalPosition(goalPos);
           }
           else{
               maze.setGoalPosition(checkWhenNoGoalPosition(start,-1));
           }
        }


        return maze;
    }

    private Position checkWhenNoGoalPosition(Position start, int num) {
        if(start.getRowIndex() + num >= 0 && start.getRowIndex() + num < maze.getHeight()){
            return new Position(start.getRowIndex() + num,start.getColumnIndex());
        }
        else if(start.getColumnIndex() + num >= 0 && start.getColumnIndex() + num< maze.getWidth()) {
            return new Position(start.getRowIndex() , start.getColumnIndex()+ num);
        }
        return null;
    }

    /**
     * @param pos
     * if it's a neighbour of the position and wall(num = 1) will add to the walls's list
     */
    private void insertNeighboursToList(Position pos)
    {
        try {
            if ((pos.getRowIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex() - 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() - 1,pos.getColumnIndex(),pos));
            if ((pos.getColumnIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() - 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() - 1, pos));
            if ((pos.getRowIndex() + 1 < maze.getHeight()) && (maze.getMazeArray()[pos.getRowIndex() + 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() + 1,pos.getColumnIndex(), pos));
            if ((pos.getColumnIndex() + 1 < maze.getWidth()) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() + 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() + 1, pos));
        }
        catch (Exception e)
        {

            //Ignore IndexArrayOutOfBound
        }

    }

}


