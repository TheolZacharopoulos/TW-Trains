package queries.errors;

/**
 * Represents a false parameter in a query error
 */
public class WrongQueryParameterValueException extends Exception {

    public WrongQueryParameterValueException() {
        super();
    }

    public WrongQueryParameterValueException(String message) {
        super(message);
    }
}