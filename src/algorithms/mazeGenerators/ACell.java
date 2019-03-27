package algorithms.mazeGenerators;

public abstract class ACell {
    private int isVisited;

    public int getVisited() {
        return isVisited;
    }

    public void setVisited(int visited) {
        isVisited = visited;
    }

    @Override
    public abstract String toString();
}
