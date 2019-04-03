package algorithms.search;

import java.util.Objects;

public abstract class AState implements Comparable<AState>
{
    protected String state;
    protected int cost;
    protected AState parent;

    /**
     * Get parent
     * @return parent
     */
    public AState getParent() {
        return parent;
    }

    /**
     *Set parent
     * @param parent
     */
    public void setParent(AState parent) {
        this.parent = parent;
    }

    /**
     * check if two states are equals
     */
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
