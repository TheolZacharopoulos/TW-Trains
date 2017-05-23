package queries;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.Graph;
import graph.algorithms.*;
import graph.algorithms.shortest_path.DijkstraShortestPathAlgorithm;
import graph.algorithms.shortest_path.ShortestPathAlgorithm;
import graph.parser.CharGraphParser;
import org.junit.Before;
import org.junit.Test;
import queries.errors.MissingQueryParametersException;
import railway.railway_query.ShortestRouteQuery;
import railway.RailwayMap;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShortestRouteQueryTest {
    private RailwayMap<Character> map;
    private ShortestPathAlgorithm algorithm;

    @Before
    public void setup() throws Exception {
        Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        CharGraphParser graphParser = new CharGraphParser(graph);

        graphParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        map = new RailwayMap<Character>("Kiwiland", graph);

        GraphAlgorithmProvider algorithmProvider = new GraphAlgorithmProvider();
        algorithm = algorithmProvider.getShortestPathAlgorithm(DijkstraShortestPathAlgorithm.ALGORITHM_NAME);
    }
    
    @Test(expected = MissingQueryParametersException.class)
    public void testMissingFrom() throws Exception {
        new ShortestRouteQuery<Character>(map, null, 'C', algorithm)
            .execute();
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingTo() throws Exception {
        new ShortestRouteQuery<Character>(map, 'A', null, algorithm)
            .execute();
    }

    @Test
    public void testShortestRouteQuery_1() throws Exception {
        // The length of the shortest route (in terms of distance to travel) from A to C.
        final Optional<Integer> res =
                new ShortestRouteQuery<Character>(map, 'A', 'C', algorithm)
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    @Test
    public void testShortestRouteQuery_2() throws Exception {
        // The length of the shortest route (in terms of distance to travel) from B to B.
        final Optional<Integer> res =
                new ShortestRouteQuery<Character>(map, 'B', 'B', algorithm)
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

}