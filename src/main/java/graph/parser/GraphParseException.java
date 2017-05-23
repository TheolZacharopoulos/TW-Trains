package graph.parser;

import parser.ParseException;

/**
 * Represents an error while parsing a graph
 */
public class GraphParseException extends ParseException {
    public GraphParseException() {
        super();
    }

    public GraphParseException(String message) {
        super(message);
    }
}
