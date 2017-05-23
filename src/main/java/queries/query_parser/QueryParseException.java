package queries.query_parser;

import parser.ParseException;

public class QueryParseException extends ParseException {
    public QueryParseException() {
        super();
    }

    public QueryParseException(String message) {
        super(message);
    }
}
