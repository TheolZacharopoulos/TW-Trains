package railway.railway_query_parser;

import queries.query_parser.QueryParseException;
import railway.RailwayMap;

public class CharRailwayQueryParser extends RailwayQueryParser<Character> {

    public CharRailwayQueryParser(RailwayMap<Character> map) {
        super(map);
    }

    protected Character getVertexFromStringAfterIndexAndKeyword(String input, int index, String keyword) throws QueryParseException {
        try {
            return input.substring(index + keyword.length() + 1, index + keyword.length() + 2).charAt(0);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    @Override
    protected Character getVertexElementFromRouteVertexData(String routeVertexData) throws QueryParseException {
        try {
            return routeVertexData.charAt(0);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }
}
