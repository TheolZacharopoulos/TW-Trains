package railway.railway_query_parser;

import railway.RailwayMap;

public class CharRailwayQueryParser extends RailwayQueryParser<Character> {

    public CharRailwayQueryParser(RailwayMap<Character> map) {
        super(map);
    }

    protected Character getVertexFromStringAfterIndexAndKeyword(String input, int index, String keyword) {
        return input.substring(index + keyword.length() + 1, index + keyword.length() + 2).charAt(0);
    }

    @Override
    protected Character getVertexElementFromRouteVertexData(String routeVertexData) {
        return routeVertexData.charAt(0);
    }
}
