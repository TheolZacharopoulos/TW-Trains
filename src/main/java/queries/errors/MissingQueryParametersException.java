package queries.errors;

/**
 * Represents a missing parameter in a query error
 */
public class MissingQueryParametersException extends Exception {

    public MissingQueryParametersException() {
        super();
    }

    public MissingQueryParametersException(String message) {
        super(message);
    }
}