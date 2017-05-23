package queries;

import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;

import java.util.Optional;

/**
 * Represents a query which can be executed.
 * @param <R> The return type pf the query, it is optional, empty in case of no results
 */
public interface Query<R> {

    /**
     * Execute the query
     * @return the query's result, it is optional, empty in case of no results
     * @throws MissingQueryParametersException in case a parameter is missing
     * @throws WrongQueryParameterValueException in case a parameter is wrong
     */
    Optional<R> execute() throws MissingQueryParametersException, WrongQueryParameterValueException;
}
