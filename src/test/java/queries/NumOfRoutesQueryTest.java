package queries;

import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.Graph;
import graph.algorithms.*;
import graph.algorithms.count_paths.CountPathsAlgorithm;
import graph.algorithms.count_paths.DFSCountPathsAlgorithm;
import graph.parser.CharGraphParser;
import org.junit.Before;
import org.junit.Test;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.railway_query.NumOfRoutesQuery;
import railway.RailwayMap;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumOfRoutesQueryTest {
    private RailwayMap<Character> map;
    private CountPathsAlgorithm algorithm;

    @Before
    public void setup() throws Exception {
        Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        CharGraphParser graphParser = new CharGraphParser(graph);

        graphParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        map = new RailwayMap<Character>("Kiwiland", graph);

        GraphAlgorithmProvider algorithmProvider = new GraphAlgorithmProvider();
        algorithm = algorithmProvider.getCountPathsAlgorithm(DFSCountPathsAlgorithm.ALGORITHM_NAME);
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingFrom() throws Exception {
            new NumOfRoutesQuery<Character>(map, algorithm)
                    .to('C')
                .execute();
    }

    @Test(expected = MissingQueryParametersException.class)
    public void testMissingTo() throws Exception {
            new NumOfRoutesQuery<Character>(map, algorithm)
                    .from('A')
                .execute();
    }

    @Test(expected = WrongQueryParameterValueException.class)
    public void testExactStops() throws Exception {
            new NumOfRoutesQuery<Character>(map, algorithm)
                    .from('C')
                    .to('C')
                    .exactStops(-1)
                .execute();
    }

    @Test(expected = WrongQueryParameterValueException.class)
    public void testMaxStops() throws Exception {
            new NumOfRoutesQuery<Character>(map, algorithm)
                    .from('C')
                    .to('C')
                    .maxStops(-1)
                .execute();
    }

    @Test
    public void NumOfRoutesQuery_1() throws Exception {
        final Optional<Integer> res =
                new NumOfRoutesQuery<Character>(map, algorithm)
                        .from('C')
                        .to('C')
                        .maxStops(3)
                    .execute();

        assertTrue(res.isPresent());
        assertEquals(2, (int) res.get());
    }

    @Test
    public void NumOfRoutesQuery_2() throws Exception {
        final Optional<Integer> res =
                new NumOfRoutesQuery<Character>(map, algorithm)
                        .from('A')
                        .to('C')
                        .exactStops(4)
                        .execute();

        assertTrue(res.isPresent());
        assertEquals(3, (int) res.get());
    }

    @Test
    public void NumOfRoutesQuery_3() throws Exception {
        final Optional<Integer> res =
                new NumOfRoutesQuery<Character>(map, algorithm)
                        .from('C')
                        .to('C')
                        .maxDistance(30)
                        .execute();

        assertTrue(res.isPresent());
        assertEquals(7, (int) res.get());
    }
}