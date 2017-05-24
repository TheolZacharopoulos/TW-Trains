package railway.railway_query_parser;

import queries.query_parser.QueryParseException;
import railway.RailwayMap;

/**
 * A concrete implementation for a railway parser which parses Character nodes
 */
public class CharRailwayQueryParser extends RailwayQueryParser<Character> {

    public CharRailwayQueryParser(RailwayMap<Character> map) {
        super(map);
    }

    protected Character getVertexFromStringAfter(String input, int index, String keyword) throws QueryParseException {
        try {
            return input.substring(index + keyword.length() + 1, index + keyword.length() + 2).charAt(0);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    @Override
    protected Character getVertexFromRouteVertex(String routeVertex) throws QueryParseException {
        try {
            return routeVertex.charAt(0);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }
}
