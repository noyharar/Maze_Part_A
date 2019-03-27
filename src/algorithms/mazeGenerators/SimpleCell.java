package algorithms.mazeGenerators;

public class SimpleCell extends ACell {
   /* private int cell;

    public int getCell() {
        return cell;
    }

    public void setCell(ACell cell) {

        this.cell = cell.cell;
    }
*/

    @Override
    public String toString() {
        return Integer.toString(this.getVisited());
    }
}
