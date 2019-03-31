package algorithms.search;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AState)) return false;
        AState aState = (AState) o;
        return Double.compare(aState.cost, cost) == 0 &&
                state.equals(aState.state) &&
                parent.equals(aState.parent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(state);
    }
}
