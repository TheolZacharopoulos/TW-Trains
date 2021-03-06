import graph.Graph;
import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.parser.CharGraphParser;
import graph.parser.GraphParseException;
import org.junit.Test;
import queries.QueryExecutor;
import railway.RailwayMap;
import railway.RailwayQueryExecutor;
import railway.railway_query_parser.CharRailwayQueryParser;
import railway.railway_query_parser.RailwayQueryParser;
import utils.FileUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

public class IntegrationTest {
    private final String mapFilename = getClass().getClassLoader().getResource("map.txt").getPath();
    private final String queriesFilename = getClass().getClassLoader().getResource("queries.txt").getPath();

    @Test
    public void testDummyData() throws Exception {
        final String mapDescription = FileUtils.readFileContents(mapFilename);
        final RailwayMap<Character> railwayMap = buildRailwayMap("Kiwiland", mapDescription);

        final List<String> queryInstructions = FileUtils.readFileContentsLineByLine(queriesFilename);

        final RailwayQueryParser<Character> queryParser = new CharRailwayQueryParser(railwayMap);
        final QueryExecutor<Integer> queryExecutor = new RailwayQueryExecutor<Character>(queryParser);

        queryExecutor.loadQueries(queryInstructions);

        final List<Optional<Integer>> results = new LinkedList<>();
        queryExecutor.executeAll(results::add);

        assertEquals(10, results.size());

        checkResultContent(results, 0, 9);
        checkResultContent(results, 1, 5);
        checkResultContent(results, 2, 13);
        checkResultContent(results, 3, 22);
        assertFalse(results.get(4).isPresent());
        checkResultContent(results, 5, 2);
        checkResultContent(results, 6, 3);
        checkResultContent(results, 7, 9);
        checkResultContent(results, 8, 9);
        checkResultContent(results, 9, 7);
    }

    private static void checkResultContent(List<Optional<Integer>> results, int index, int result) throws Exception {
        assertTrue(results.get(index).isPresent());
        assertEquals(result, (int) results.get(index).get());
    }

    private static RailwayMap<Character> buildRailwayMap(String landName, String mapDescription) throws GraphParseException {
        // parse map
        final Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
        final CharGraphParser graphParser = new CharGraphParser(graph);
        graphParser.parse(mapDescription);

        return new RailwayMap<>(landName, graph);
    }
}
