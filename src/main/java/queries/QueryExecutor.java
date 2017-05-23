package queries;

import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import queries.query_parser.QueryParseException;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface QueryExecutor<R> {
    void loadQueries(List<String> queryInstructions) throws QueryParseException;

    void executeAll(Consumer<Optional<Integer>> queryResultConsumer) throws MissingQueryParametersException, WrongQueryParameterValueException;

    default Optional<R> execute(Query<R> query) throws MissingQueryParametersException, WrongQueryParameterValueException {
        return query.execute();
    }
}
