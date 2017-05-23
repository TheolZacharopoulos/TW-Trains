package queries.errors;

public class MissingQueryParametersException extends Exception {

    public MissingQueryParametersException() {
        super();
    }

    public MissingQueryParametersException(String message) {
        super(message);
    }
}