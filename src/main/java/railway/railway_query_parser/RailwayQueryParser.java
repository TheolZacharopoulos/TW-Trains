package railway.railway_query_parser;

import queries.Query;
import queries.query_parser.QueriesParser;
import queries.query_parser.QueryParseException;
import railway.RailwayMap;
import railway.railway_query.NumOfRoutesQuery;
import railway.railway_query.RouteDistanceQuery;
import railway.railway_query.ShortestRouteQuery;

public class RailwayQueryParser extends QueriesParser<Integer> {
    private final RailwayMap<Character> map;

    // Route Distance
    private final String ROUTE_DISTANCE_STARTS_WITH = "The distance of";
    private final String ROUTE_DISTANCE_ROUTE = "the route";

    // Number Of Routes
    private final String NUMBER_OF_ROUTES_STARTS_WITH_1 = "The number of trips";
    private final String NUMBER_OF_ROUTES_STARTS_WITH_2 = "The number of different routes";

    private final String NUMBER_OF_ROUTES_FROM = "from";
    private final String NUMBER_OF_ROUTES_STARTING = "starting at";
    private final String NUMBER_OF_ROUTES_TO = "to";
    private final String NUMBER_OF_ROUTES_ENDING = "ending at";

    private final String NUMBER_OF_ROUTES_MAX_STOPS_START = "with a maximum of";
    private final String NUMBER_OF_ROUTES_MAX_STOPS_END = "stops";

    private final String NUMBER_OF_ROUTES_EXACT_STOPS_START = "with exactly";
    private final String NUMBER_OF_ROUTES_EXACT_STOPS_END = "stops";

    private final String NUMBER_OF_ROUTES_MAX_DISTANCE = "with a distance of less than";

    // Shortest Route
    private final String SHORTEST_ROUTE_STARTS_WITH = "The length of the shortest route";
    private final String SHORTEST_ROUTE_FROM = "from";
    private final String SHORTEST_ROUTE_TO = "to";

    public RailwayQueryParser(RailwayMap<Character> map) {
        this.map = map;
    }

    @Override
    public Query<Integer> parse(String input) throws QueryParseException {
        if (input.startsWith(ROUTE_DISTANCE_STARTS_WITH)) {
            return parseRouteDistanceQuery(input);
        } else if (input.startsWith(NUMBER_OF_ROUTES_STARTS_WITH_1) || input.startsWith(NUMBER_OF_ROUTES_STARTS_WITH_2)) {
            return parseNumberOfRoutesQuery(input);
        } else if (input.startsWith(SHORTEST_ROUTE_STARTS_WITH)) {
            return parseShortestRouteQuery(input);
        }

        throw new QueryParseException("Not correct input for query.");
    }

    private Query<Integer> parseRouteDistanceQuery(String input) throws QueryParseException {
        if (input.startsWith(ROUTE_DISTANCE_STARTS_WITH)) {
            final int routeKeywordIndex = input.indexOf(ROUTE_DISTANCE_ROUTE);
            if (routeKeywordIndex != -1) {
                final String route;
                try {
                    route = input.substring(routeKeywordIndex + ROUTE_DISTANCE_ROUTE.length() + 1, input.indexOf("."));
                } catch (IndexOutOfBoundsException e) {
                    throw new QueryParseException("Not correct input for query.");
                }

                if (route.isEmpty()) {
                    throw new QueryParseException("Not correct input for query.");
                }

                final String[] routeVertices = route.split("-");
                if (routeVertices.length < 2) {
                    throw new QueryParseException("Not correct input for query.");
                }

                final RouteDistanceQuery<Character> query = new RouteDistanceQuery<Character>(map);
                for (String town : routeVertices) {
                    query.addTownStop(town.charAt(0));
                }
                return query;

            } else {
                throw new QueryParseException("Not correct input for query.");
            }
        }
        throw new QueryParseException("Not correct input for query.");
    }

    private Query<Integer> parseNumberOfRoutesQuery(String input) throws QueryParseException {
        if (input.startsWith(NUMBER_OF_ROUTES_STARTS_WITH_1) || input.startsWith(NUMBER_OF_ROUTES_STARTS_WITH_2)) {
            final int fromIndex;
            final int toIndex;
            final String fromKeyWord;
            final String toKeyWord;

            if (input.contains(NUMBER_OF_ROUTES_FROM)) {
                fromIndex = input.indexOf(NUMBER_OF_ROUTES_FROM);
                if (input.contains(NUMBER_OF_ROUTES_TO)) {
                    toIndex = input.indexOf(NUMBER_OF_ROUTES_TO, fromIndex);

                    fromKeyWord = NUMBER_OF_ROUTES_FROM;
                    toKeyWord = NUMBER_OF_ROUTES_TO;
                } else {
                    throw new QueryParseException("Not correct input for query.");
                }

            } else if (input.contains(NUMBER_OF_ROUTES_STARTING)) {
                fromIndex = input.indexOf(NUMBER_OF_ROUTES_STARTING);
                if (input.contains(NUMBER_OF_ROUTES_ENDING)) {
                    toIndex = input.indexOf(NUMBER_OF_ROUTES_ENDING, fromIndex);

                    fromKeyWord = NUMBER_OF_ROUTES_STARTING;
                    toKeyWord = NUMBER_OF_ROUTES_ENDING;
                } else {
                    throw new QueryParseException("Not correct input for query.");
                }

            } else {
                throw new QueryParseException("Not correct input for query.");
            }

            final Character from;
            final Character to;
            try {
                from = input.substring(fromIndex + fromKeyWord.length() + 1, fromIndex + fromKeyWord.length() + 2).charAt(0);
                to = input.substring(toIndex + toKeyWord.length() + 1, toIndex + toKeyWord.length() + 2).charAt(0);
            } catch (IndexOutOfBoundsException e) {
                throw new QueryParseException("Not correct input for query.");
            }

            // it is max stops query
            if (input.contains(NUMBER_OF_ROUTES_MAX_STOPS_START) && input.contains(NUMBER_OF_ROUTES_MAX_STOPS_END)) {
                final int maxIndex = input.indexOf(NUMBER_OF_ROUTES_MAX_STOPS_START, toIndex);
                try {
                    final String stopsStr = input.substring(
                            maxIndex + NUMBER_OF_ROUTES_MAX_STOPS_START.length() + 1,
                            input.indexOf(" ", maxIndex + NUMBER_OF_ROUTES_MAX_STOPS_START.length() + 1));

                    return new NumOfRoutesQuery<>(map, from, to)
                            .maxStops(Integer.parseInt(stopsStr));

                } catch (Exception e) {
                    throw new QueryParseException("Not correct input for query.");
                }

            // it is exact stops query
            } else if (input.contains(NUMBER_OF_ROUTES_EXACT_STOPS_START) && input.contains(NUMBER_OF_ROUTES_EXACT_STOPS_END)) {

                final int exactIndex = input.indexOf(NUMBER_OF_ROUTES_EXACT_STOPS_START, toIndex);
                try {
                    final String stopsStr = input.substring(
                            exactIndex + NUMBER_OF_ROUTES_EXACT_STOPS_START.length() + 1,
                            input.indexOf(" ", exactIndex + NUMBER_OF_ROUTES_EXACT_STOPS_START.length() + 1));

                    return new NumOfRoutesQuery<>(map, from, to)
                            .exactStops(Integer.parseInt(stopsStr));

                } catch (Exception e) {
                    throw new QueryParseException("Not correct input for query.");
                }

            // it is max distance query
            } else if (input.contains(NUMBER_OF_ROUTES_MAX_DISTANCE)) {
                final int lessIndex = input.indexOf(NUMBER_OF_ROUTES_MAX_DISTANCE, toIndex);
                try {
                    final String distanceStr = input.substring(
                            lessIndex + NUMBER_OF_ROUTES_MAX_DISTANCE.length() + 1,
                            input.indexOf(".", lessIndex + NUMBER_OF_ROUTES_MAX_DISTANCE.length() + 1));

                    return new NumOfRoutesQuery<>(map, from, to)
                            .maxDistance(Integer.parseInt(distanceStr));

                } catch (Exception e) {
                    throw new QueryParseException("Not correct input for query.");
                }

            // no query matches
            } else {
                throw new QueryParseException("Not correct input for query.");
            }
        }
        throw new QueryParseException("Not correct input for query.");
    }

    private Query<Integer> parseShortestRouteQuery(String input) throws QueryParseException {
        if (input.startsWith(SHORTEST_ROUTE_STARTS_WITH)) {
            final int fromIndex = input.indexOf(SHORTEST_ROUTE_FROM);
            final int toIndex = input.indexOf(SHORTEST_ROUTE_TO, fromIndex);

            if (fromIndex != -1 && toIndex != -1) {
                final Character from;
                final Character to;
                try {
                    from = input.substring(fromIndex + SHORTEST_ROUTE_FROM.length() + 1, fromIndex + SHORTEST_ROUTE_FROM.length() + 2).charAt(0);
                    to = input.substring(toIndex + SHORTEST_ROUTE_TO.length() + 1, toIndex + SHORTEST_ROUTE_TO.length() + 2).charAt(0);
                } catch (IndexOutOfBoundsException e) {
                    throw new QueryParseException("Not correct input for query.");
                }
                return new ShortestRouteQuery<>(map, from, to);
            } else {
                throw new QueryParseException("Not correct input for query.");
            }
        }
        throw new QueryParseException("Not correct input for query.");
    }
}
