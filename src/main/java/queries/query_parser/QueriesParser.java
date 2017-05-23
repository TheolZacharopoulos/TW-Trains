package queries.query_parser;

import parser.Parser;
import queries.Query;

public abstract class QueriesParser<R> implements Parser<String, Query<R>> {
    @Override
    abstract public Query<R> parse(String input) throws QueryParseException;
}
