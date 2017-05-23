package graph;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.errors.GraphException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectedGraphTest {

    AdjacencyMapDirectedGraph<Character> g;

    @Before
    public void setup() {
        g = new AdjacencyMapDirectedGraph<>();
    }

    @Test
    public void testGraphCreation_checkEdges() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');

        assertEquals(3, g.numberOfVertices()); // A, B, C
        assertEquals(2, g.numberOfEdges());    // AB, AC
    }

    @Test
    public void testGraphCreation_edges() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');

        assertEquals(2, g.getEdges('A').size());

        assertEquals(0, g.getEdges('B').size());
        assertEquals(0, g.getEdges('C').size());
    }

    @Test
    public void testGraphCreation_all_edges() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');
        g.insertEdge('C', 'A');

        assertEquals(3, g.getEdges().size());
    }

    @Test
    public void testGraphCreation_edges_direction() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');
        g.insertEdge('C', 'A');

        assertEquals(2, g.getEdges('A').size());

        assertEquals(0, g.getEdges('B').size());

        assertEquals(1, g.getEdges('C').size());
    }

    @Test(expected = GraphException.class)
    public void testGraphCreation_edges_duplicate() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');
        g.insertEdge('A', 'C');
    }

    @Test
    public void testGraphCreation_adjacent() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');
        g.insertEdge('C', 'A');

        assertEquals(2, g.adjacent('A').size());
    }
}