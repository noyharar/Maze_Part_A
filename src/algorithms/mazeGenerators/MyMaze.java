package algorithms.mazeGenerators;

import java.util.Arrays;

public class MyMaze extends Maze {

    public MyMaze(ACell[][] maze) {
        super(maze);
    }

    @Override
    public void print() {

        for (int i = 0; i < maze[0].length-1; i++) {
            System.out.print(" _");
        }
        System.out.println();
        for (int i = 0; i < maze.length-1; i++) {
            for (int j = 0; j < maze[i].length-1; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String printed = "";
        printed += "a";
        return printed;
    }
}
