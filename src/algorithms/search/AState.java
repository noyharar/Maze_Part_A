package algorithms.search;

public abstract class AState
{
    protected String state;
    protected double cost;
    protected AState parent;


    public AState getParent() {
        return parent;
    }

    public void setParent(AState parent) {
        this.parent = parent;
    }
}
