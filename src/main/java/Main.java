import graph.Graph;
import graph.adjacency_map.AdjacencyMapDirectedGraph;
import graph.parser.CharGraphParser;
import graph.parser.GraphParseException;
import queries.QueryExecutor;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import queries.query_parser.QueryParseException;
import railway.RailwayMap;
import railway.RailwayQueryExecutor;
import railway.railway_query_parser.CharRailwayQueryParser;
import railway.railway_query_parser.RailwayQueryParser;
import utils.FileUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar trains.jar <land> <railway map filename> <queries filename>");
            System.exit(0);
        }

        final String landName = args[0];
        final String mapFilename = args[1];
        final String queriesFilename = args[2];

        try {
            // Read the input from files, map and queries
            final String mapDescription = FileUtils.readFileContents(mapFilename);
            final List<String> queryInstructions = FileUtils.readFileContentsLineByLine(queriesFilename);

            // Create and parse the graph
            final Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
            (new CharGraphParser(graph)).parse(mapDescription);

            // Create a map with Character type for the vertices
            final RailwayMap<Character> railwayMap = new RailwayMap<>(landName, graph);

            // Parse the queries
            final RailwayQueryParser<Character> queryParser = new CharRailwayQueryParser(railwayMap);
            final QueryExecutor<Integer> queryExecutor = new RailwayQueryExecutor<>(queryParser);
            queryExecutor.loadQueries(queryInstructions);

            // Execute the queries
            queryExecutor.executeAll(result -> {
                if (result.isPresent()) {
                    System.out.println("Output: " + result.get());
                } else {
                    System.out.println("NO SUCH ROUTE.");
                }
            });

        } catch (IOException ioE) {
            System.out.println("Reading file error - " + ioE.getLocalizedMessage());
            System.exit(1);
        } catch (GraphParseException gpE) {
            System.out.println("Parsing graph error - " + gpE.getLocalizedMessage());
            System.exit(1);
        } catch (MissingQueryParametersException mpE) {
            System.out.println("Missing query parameters - " + mpE.getLocalizedMessage());
            System.exit(1);
        } catch (WrongQueryParameterValueException wqE) {
            System.out.println("Wrong query parameters - " + wqE.getLocalizedMessage());
            System.exit(1);
        } catch (QueryParseException qpE) {
            System.out.println("Wrong query syntax - " + qpE.getLocalizedMessage());
            System.exit(1);
        }
    }
}
