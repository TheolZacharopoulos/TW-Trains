package queries;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.Graph;
import graph.parser.CharGraphParser;
import org.junit.Before;
import org.junit.Test;
import queries.errors.MissingQueryParametersException;
import railway.railway_query.RouteDistanceQuery;
import railway.RailwayMap;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouteDistanceQueryTest {

    private RailwayMap<Character> map;

    @Before
    public void setup() throws Exception {
        Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        CharGraphParser graphParser = new CharGraphParser(graph);

        graphParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        map = new RailwayMap<Character>("Kiwiland", graph);
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingTowns_none() throws Exception {
        new RouteDistanceQuery<Character>(map)
            .execute();
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingTowns_only_one() throws Exception {
        new RouteDistanceQuery<Character>(map)
                .addTownStop('A')
            .execute();
    }

    @Test
    public void testDistanceQuery_1() throws Exception {
        // The distance of the route A-B-C.
        final Optional<Integer> res =
                new RouteDistanceQuery<Character>(map)
                    .addTownStop('A')
                    .addTownStop('B')
                    .addTownStop('C')
                .execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    @Test
    public void testDistanceQuery_2() throws Exception {
        // The distance of the route A-D.
        final Optional<Integer> res =
                new RouteDistanceQuery<Character>(map)
                        .addTownStop('A')
                        .addTownStop('D')
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(5, (int) res.get());
    }

    @Test
    public void testDistanceQuery_3() throws Exception {
        // The distance of the route A-D-C.
        final Optional<Integer> res =
                new RouteDistanceQuery<Character>(map)
                        .addTownStop('A')
                        .addTownStop('D')
                        .addTownStop('C')
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(13, (int) res.get());
    }

    @Test
    public void testDistanceQuery_4() throws Exception {
        // 4. The distance of the route A-E-B-C-D.
        final Optional<Integer> res =
                new RouteDistanceQuery<Character>(map)
                        .addTownStop('A')
                        .addTownStop('E')
                        .addTownStop('B')
                        .addTownStop('C')
                        .addTownStop('D')
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(22, (int) res.get());
    }

    @Test
    public void testDistanceQuery_5() throws Exception {
        // The distance of the route A-E-D.
        final Optional<Integer> res =
                new RouteDistanceQuery<Character>(map)
                        .addTownStop('A')
                        .addTownStop('E')
                        .addTownStop('D')
                    .execute();

        assertFalse(res.isPresent());
    }
}