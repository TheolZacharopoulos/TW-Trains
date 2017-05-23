package graph.parser;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.Graph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphParserTest {
    Graph<Character> graph;
    CharGraphParser graphParser;

    @Before
    public void setup() {
        graph = new AdjacencyMapDirectedGraph<>();
        graphParser = new CharGraphParser(graph);
    }

    @Test(expected = GraphParseException.class)
    public void testWrongGraphConstruction_bad_weight() throws Exception {
        String graphDescription = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AEXX";
        graphParser.parse(graphDescription);
    }

    @Test(expected = GraphParseException.class)
    public void testWrongGraphConstruction_bad_vertex() throws Exception {
        String graphDescription = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, 3E7";
        graphParser.parse(graphDescription);
    }

    @Test(expected = GraphParseException.class)
    public void testWrongGraphConstruction_long_vertex() throws Exception {
        String graphDescription = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AAE7";
        graphParser.parse(graphDescription);
    }

    @Test(expected = GraphParseException.class)
    public void testGraphConstruction_duplicate_def() throws Exception {
        String graphDescription = "AB5, BC4, CD8, DC8, DC8, DE6, AD5, CE2, EB3, AE7";
        graphParser.parse(graphDescription);

    }

    @Test
    public void testGraphConstruction() throws Exception {
        String graphDescription = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        graphParser.parse(graphDescription);

        assertEquals(5, graph.numberOfVertices()); // A, B, C, D, E
        assertEquals(9, graph.numberOfEdges()); // AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

        assertEquals(3, graph.getEdges('A').size()); // AB5, AD5, AE7
    }
}