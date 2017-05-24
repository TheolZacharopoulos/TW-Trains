package railway;

import queries.Query;
import queries.QueryExecutor;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import queries.query_parser.QueryParseException;
import railway.railway_query_parser.RailwayQueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Is responsible for executing a set of railway queries.
 */
public class RailwayQueryExecutor<T> implements QueryExecutor<Integer> {
    private final List<Query<Integer>> queries;
    private final RailwayQueryParser<T> queryParser;

    public RailwayQueryExecutor(RailwayQueryParser<T> queryParser) {
        this.queryParser = queryParser;
        this.queries = new ArrayList<>();
    }

    public void loadQueries(List<String> queryInstructions) throws QueryParseException {
        for (String queryInstruction : queryInstructions) {
            queries.add(queryParser.parse(queryInstruction));
        }
    }

    @Override
    public void executeAll(Consumer<Optional<Integer>> queryResultConsumer)
            throws MissingQueryParametersException, WrongQueryParameterValueException
    {
        // execute each query and provide its result to the consumer
        queries.stream()
            .map(this::safeQueryExecution)
            .forEach(queryResultConsumer);
    }

    private Optional<Integer> safeQueryExecution(Query<Integer> query) {
        try {
            return query.execute();
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
