package queries.errors;

public class WrongQueryParameterValueException extends Exception {

    public WrongQueryParameterValueException() {
        super();
    }

    public WrongQueryParameterValueException(String message) {
        super(message);
    }
}