package algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstSearchTest {

    @Test
    void solve() {
    }

    @Test
    void getName() {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        assertEquals("Breadth First Search",bfs.getName());
    }
}