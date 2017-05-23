package queries;

import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import queries.query_parser.QueryParseException;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Is responsible for executing a set of queries.
 * @param <R> the query's result type
 */
public interface QueryExecutor<R> {
    /**
     * Load a set of queries from a set of instructions
     * @param queryInstructions the queries instructions
     * @throws QueryParseException in case of error while parsing the instructions
     */
    void loadQueries(List<String> queryInstructions) throws QueryParseException;

    /**
     * Execute the set of loaded queries
     * @param queryResultConsumer A Functional Interface (Consumer) which consumes the results of the query
     * @throws MissingQueryParametersException in case a parameter is missing
     * @throws WrongQueryParameterValueException in case a parameter is wrong
     */
    void executeAll(Consumer<Optional<R>> queryResultConsumer) throws MissingQueryParametersException, WrongQueryParameterValueException;
}
