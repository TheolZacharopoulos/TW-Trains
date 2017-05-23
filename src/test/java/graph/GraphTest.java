package graph;

import graph.adjacency_map.AdjacencyMapGraph;
import graph.errors.GraphException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphTest {

    AdjacencyMapGraph<Character> g;

    @Before
    public void setup() {
        g = new AdjacencyMapGraph<>();
    }

    @Test
    public void testGraphCreation_checkVertices() {
        g.insertVertex('A');
        g.insertVertex('B');

        assertEquals(2, g.numberOfVertices());
    }

    @Test(expected = GraphException.class)
    public void testGraphCreation_vertices_do_not_exist() throws Exception {
        g.insertVertex('A');
        g.insertVertex('B');

        g.checkVertex(new Vertex<Character>('V'));
    }

    @Test
    public void testGraphCreation_checkEdges() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');

        assertEquals(3, g.numberOfVertices()); // A, B, C
        assertEquals(4, g.numberOfEdges());    // AB, AC, BA, CA
    }

    @Test
    public void testGraphCreation_edges() throws Exception {
        g.insertEdge('A', 'B');
        g.insertEdge('A', 'C');

        assertEquals(2, g.getEdges('A').size());

        assertEquals(1, g.getEdges('B').size());

        assertEquals(1, g.getEdges('C').size());
    }
}