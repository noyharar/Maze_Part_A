package algorithms.mazeGenerators;

public interface IMazeGenerator {

  public Maze generate(int height, int width);
  public long measureAlgorithmTimeMillis(int height, int width);

}
