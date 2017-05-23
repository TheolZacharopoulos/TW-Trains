package queries;

import graph.Graph;
import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.parser.CharGraphParser;
import org.junit.Before;
import org.junit.Test;
import queries.query_parser.QueryParseException;
import railway.RailwayMap;
import railway.railway_query.NumOfRoutesQuery;
import railway.railway_query.RouteDistanceQuery;
import railway.railway_query.ShortestRouteQuery;
import railway.railway_query_parser.RailwayQueryParser;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RailwayQueryParserTest {

    private RailwayQueryParser<Character> queryParser;

    @Before
    public void setup() throws Exception {
        Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        CharGraphParser graphParser = new CharGraphParser(graph);

        graphParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        RailwayMap<Character> map = new RailwayMap<Character>("Kiwiland", graph);

        queryParser = new RailwayQueryParser<Character>(map);
    }

    // =========================
    // Parse RouteDistanceQuery
    @Test(expected = QueryParseException.class)
    public void testWrongInput_routeDistance_missing_distance_keyword() throws Exception {
        final String instructions = "The X of the route A-B-C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_routeDistance_missing_route_keyword() throws Exception {
        final String instructions = "The distance of the X A-B-C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_routeDistance_missing_route() throws Exception {
        final String instructions = "The distance of the route.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test
    public void testDistanceRouteCMD_1() throws Exception {
        final String instructions = "The distance of the route A-B-C.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof RouteDistanceQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    @Test
    public void testDistanceRouteCMD_2() throws Exception {
        final String instructions = "The distance of the route A-D.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof RouteDistanceQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(5, (int) res.get());
    }

    @Test
    public void testDistanceRouteCMD_3() throws Exception {
        final String instructions = "The distance of the route A-D-C.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof RouteDistanceQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(13, (int) res.get());
    }

    @Test
    public void testDistanceRouteCMD_4() throws Exception {
        final String instructions = "The distance of the route A-E-B-C-D.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof RouteDistanceQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(22, (int) res.get());
    }

    @Test
    public void testDistanceRouteCMD_5() throws Exception {
        final String instructions = "The distance of the route A-E-D.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof RouteDistanceQuery);

        final Optional<Integer> res = query.execute();

        assertFalse(res.isPresent());
    }

    // =========================
    // Parse ShortestRouteQuery
    @Test(expected = QueryParseException.class)
    public void testWrongInput_shortestRoute_missing_shortest_keyword() throws Exception {
        final String instructions = "The length of the route (in terms of distance to travel) from A to C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_shortestRoute_missing_route_keyword() throws Exception {
        final String instructions = "The length of the shortest (in terms of distance to travel) from A to C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_shortestRoute_missing_from_keyword() throws Exception {
        final String instructions = "The length of the shortest route (in terms of distance to travel) A to C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_shortestRoute_missing_to_keyword() throws Exception {
        final String instructions = "The length of the shortest route (in terms of distance to travel) from A C.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test
    public void testShortestRouteCMD_1() throws Exception {
        final String instructions = "The length of the shortest route (in terms of distance to travel) from A to C.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof ShortestRouteQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    @Test
    public void testShortestRouteCMD_2() throws Exception {
        final String instructions = "The length of the shortest route (in terms of distance to travel) from B to B.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof ShortestRouteQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(9, (int) res.get());
    }

    // =========================
    // Parse NumOfRoutesQuery
    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_number_keyword() throws Exception {
        final String instructions = "The X of trips starting at C and ending at C with a maximum of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_trips_keyword() throws Exception {
        final String instructions = "The number of X starting at C and ending at C with a maximum of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_s_at_keyword() throws Exception {
        final String instructions = "The number of trips starting C and ending at C with a maximum of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_e_at_keyword() throws Exception {
        final String instructions = "The number of trips starting at C and ending C with a maximum of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_from_keyword() throws Exception {
        final String instructions = "The number of different routes C to C with a distance of less than 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_to_keyword() throws Exception {
        final String instructions = "The number of different routes from C C with a distance of less than 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_max_keyword() throws Exception {
        final String instructions = "The number of trips starting at C and ending at C with a of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_of_keyword() throws Exception {
        final String instructions = "The number of trips starting at C and ending at C with a maximum 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_exactly_keyword() throws Exception {
        final String instructions = "The number of trips starting at A and ending at C with 4 stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_stops_not_number() throws Exception {
        final String instructions = "The number of trips starting at A and ending at C with exactly FFFF stops.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_distance_keyword() throws Exception {
        final String instructions = "The number of different routes from C to C with a of less than 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_distance_of_keyword() throws Exception {
        final String instructions = "The number of different routes from C to C with a distance less than 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_distance_less_keyword() throws Exception {
        final String instructions = "The number of different routes from C to C with a distance of than 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_distance_than_keyword() throws Exception {
        final String instructions = "The number of different routes from C to C with a distance of less 30.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test(expected = QueryParseException.class)
    public void testWrongInput_numOfRoutes_missing_distance_not_number() throws Exception {
        final String instructions = "The number of different routes from C to C with a distance of less X.";
        final Query<Integer> query = queryParser.parse(instructions);
    }

    @Test
    public void testNumOfRoutesCMD_max_stops() throws Exception {
        final String instructions = "The number of trips starting at C and ending at C with a maximum of 3 stops.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof NumOfRoutesQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(2, (int) res.get());
    }

    @Test
    public void testNumOfRoutesCMD_exact_stops() throws Exception {
        final String instructions = "The number of trips starting at A and ending at C with exactly 4 stops.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof NumOfRoutesQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(3, (int) res.get());
    }

    @Test
    public void testNumOfRoutesCMD_max_distance() throws Exception {
        final String instructions = "The number of different routes from C to C with a distance of less than 30.";
        final Query<Integer> query = queryParser.parse(instructions);

        assertTrue(query instanceof NumOfRoutesQuery);

        final Optional<Integer> res = query.execute();

        assertTrue(res.isPresent());
        assertEquals(7, (int) res.get());
        }
}