package queries;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.Graph;
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

    @Before
    public void setup() throws Exception {
        Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        CharGraphParser graphParser = new CharGraphParser(graph);

        graphParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        map = new RailwayMap<Character>("Kiwiland", graph);
    }
    
    @Test(expected = MissingQueryParametersException.class)
    public void testMissingFrom() throws Exception {
        new ShortestRouteQuery<Character>(map)
                .to('C')
            .execute();
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingTo() throws Exception {
        new ShortestRouteQuery<Character>(map)
                .from('A')
            .execute();
    }

    @Test
    public void testShortestRouteQuery_1() throws Exception {
        // The length of the shortest route (in terms of distance to travel) from A to C.
        final Optional<Integer> res =
                new ShortestRouteQuery<Character>(map)
                        .from('A')
                        .to('C')
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    @Test
    public void testShortestRouteQuery_2() throws Exception {
        // The length of the shortest route (in terms of distance to travel) from B to B.
        final Optional<Integer> res =
                new ShortestRouteQuery<Character>(map)
                        .from('B')
                        .to('B')
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

}