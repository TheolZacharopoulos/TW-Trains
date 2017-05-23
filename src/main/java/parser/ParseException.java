package parser;

/**
 * Represents an error while parsing
 */
public class ParseException extends Exception {

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }
}
