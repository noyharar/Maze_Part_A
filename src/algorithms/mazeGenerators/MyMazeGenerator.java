package algorithms.mazeGenerators;
import java.util.ArrayList;


public class MyMazeGenerator extends AMazeGenerator {

    private ArrayList<Position> wallList;

    public MyMazeGenerator() {

    }

    @Override
    public Maze generate(int height, int width) {
        this.maze = new Maze(height,width);
        maze.init(1);

        maze.setStartPosition(randomPos(height,width,null));
        Position start = maze.getStartPosition();
        maze.getMazeArray()[start.getRowIndex()][start.getColumnIndex()] = 0;
        wallList = new ArrayList<>();
        insertNeighboursToList(start);

        while (!wallList.isEmpty())
        {
            try{
                int nextIndex = (int)(Math.random() * wallList.size());
                Position currentRandom = wallList.remove(nextIndex), onTheOtherEnd;


                onTheOtherEnd = currentRandom.getOtherSide();
                if(maze.getMazeArray()[currentRandom.getRowIndex()][currentRandom.getColumnIndex()] == 1)
                {
                    if(maze.getMazeArray()[onTheOtherEnd.getRowIndex()][onTheOtherEnd.getColumnIndex()] == 1)
                    {
                        maze.getMazeArray()[currentRandom.getRowIndex()][currentRandom.getColumnIndex()] = 0;
                        maze.getMazeArray()[onTheOtherEnd.getRowIndex()][onTheOtherEnd.getColumnIndex()] = 0;

                        maze.setGoalPosition(onTheOtherEnd);

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

    private void insertNeighboursToList(Position pos)
    {
        try {
            if ((pos.getRowIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex() - 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() - 1,pos.getColumnIndex(),pos));
            if ((pos.getColumnIndex() - 1 >= 0) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() - 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() - 1, pos));
            if ((pos.getRowIndex() - 1 <= maze.getHeight()) && (maze.getMazeArray()[pos.getRowIndex() + 1][pos.getColumnIndex()] == 1))
                wallList.add(new Position(pos.getRowIndex() + 1,pos.getColumnIndex(), pos));
            if ((pos.getColumnIndex() - 1 <= maze.getWidth()) && (maze.getMazeArray()[pos.getRowIndex()][pos.getColumnIndex() + 1] == 1))
                wallList.add(new Position(pos.getRowIndex() ,pos.getColumnIndex() + 1, pos));
        }
        catch (Exception e)
        {
            //Ignore IndexArrayOutOfBound
        }

    }

}


