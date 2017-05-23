package queries.query_parser;

import parser.ParseException;

/**
 * Represents an error while parsing a query
 */
public class QueryParseException extends ParseException {
    public QueryParseException() {
        super();
    }

    public QueryParseException(String message) {
        super(message);
    }
}
