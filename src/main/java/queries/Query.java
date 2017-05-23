package queries;

import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;

import java.util.Optional;

public interface Query<R> {
    Optional<R> execute() throws MissingQueryParametersException, WrongQueryParameterValueException;
}
