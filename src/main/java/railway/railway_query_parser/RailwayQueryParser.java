package railway.railway_query_parser;

import graph.algorithms.*;
import graph.algorithms.count_paths.CountPathsAlgorithm;
import graph.algorithms.count_paths.DFSCountPathsAlgorithm;
import graph.algorithms.route_distance.GreedyRouteDistanceAlgorithm;
import graph.algorithms.route_distance.RouteDistanceAlgorithm;
import graph.algorithms.shortest_path.DijkstraShortestPathAlgorithm;
import graph.algorithms.shortest_path.ShortestPathAlgorithm;
import queries.Query;
import queries.query_parser.QueriesParser;
import queries.query_parser.QueryParseException;
import railway.RailwayMap;
import railway.railway_query.NumOfRoutesQuery;
import railway.railway_query.RouteDistanceQuery;
import railway.railway_query.ShortestRouteQuery;

public abstract class RailwayQueryParser<T> extends QueriesParser<Integer> {
    private static final String ROUTES_SPLITTER = "-";

    private static final GraphAlgorithmProvider ALGORITHM_PROVIDER = new GraphAlgorithmProvider();

    private static final ShortestPathAlgorithm SHORTEST_PATH_ALGORITHM =
            ALGORITHM_PROVIDER.getShortestPathAlgorithm(DijkstraShortestPathAlgorithm.ALGORITHM_NAME);

    private static final CountPathsAlgorithm COUNT_PATHS_ALGORITHM =
            ALGORITHM_PROVIDER.getCountPathsAlgorithm(DFSCountPathsAlgorithm.ALGORITHM_NAME);

    private static final RouteDistanceAlgorithm ROUTE_DISTANCE_ALGORITHM =
            ALGORITHM_PROVIDER.getRouteDistanceAlgorithm(GreedyRouteDistanceAlgorithm.ALGORITHM_NAME);

    // Route Distance
    private static final String ROUTE_DISTANCE_STARTS_WITH = "The distance of";
    private static final String ROUTE_DISTANCE_ROUTE = "the route";

    // Number Of Routes
    private static final String NUMBER_OF_ROUTES_STARTS_WITH_1 = "The number of trips";
    private static final String NUMBER_OF_ROUTES_STARTS_WITH_2 = "The number of different routes";

    private static final String NUMBER_OF_ROUTES_FROM = "from";
    private static final String NUMBER_OF_ROUTES_STARTING = "starting at";
    private static final String NUMBER_OF_ROUTES_TO = "to";
    private static final String NUMBER_OF_ROUTES_ENDING = "ending at";

    private static final String NUMBER_OF_ROUTES_MAX_STOPS_START = "with a maximum of";
    private static final String NUMBER_OF_ROUTES_MAX_STOPS_END = "stops";

    private static final String NUMBER_OF_ROUTES_EXACT_STOPS_START = "with exactly";
    private static final String NUMBER_OF_ROUTES_EXACT_STOPS_END = "stops";

    private static final String NUMBER_OF_ROUTES_MAX_DISTANCE = "with a distance of less than";

    // Shortest Route
    private static final String SHORTEST_ROUTE_STARTS_WITH = "The length of the shortest route";
    private static final String SHORTEST_ROUTE_FROM = "from";
    private static final String SHORTEST_ROUTE_TO = "to";

    private final RailwayMap<T> map;

    public RailwayQueryParser(RailwayMap<T> map) {
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
        final int routeKeywordIndex = input.indexOf(ROUTE_DISTANCE_ROUTE);
        final String[] routeVertices;
        final String route;

        if (routeKeywordIndex != -1) {
            try {
                route = input.substring(routeKeywordIndex + ROUTE_DISTANCE_ROUTE.length() + 1, input.indexOf("."));
            } catch (IndexOutOfBoundsException e) {
                throw new QueryParseException("Not correct input for query.");
            }

            routeVertices = route.split(ROUTES_SPLITTER);
            if (routeVertices.length < 2) {
                throw new QueryParseException("Not correct input for query.");
            }

            final RouteDistanceQuery<T> query = new RouteDistanceQuery<>(map, ROUTE_DISTANCE_ALGORITHM);
            for (String routeVertexData : routeVertices) {
                T element = getVertexElementFromRouteVertexData(routeVertexData);
                query.addTownStop(element);
            }
            return query;
        }

        throw new QueryParseException("Not correct input for query.");
    }

    private Query<Integer> parseShortestRouteQuery(String input) throws QueryParseException {
        final int fromIndex = input.indexOf(SHORTEST_ROUTE_FROM);
        final int toIndex = input.indexOf(SHORTEST_ROUTE_TO, fromIndex);

        if (fromIndex != -1 && toIndex != -1) {
            final T from = getVertexFromStringAfterIndexAndKeyword(input, fromIndex, SHORTEST_ROUTE_FROM);
            final T to = getVertexFromStringAfterIndexAndKeyword(input, toIndex, SHORTEST_ROUTE_TO);
            return new ShortestRouteQuery<>(map, from, to, SHORTEST_PATH_ALGORITHM);
        }
        throw new QueryParseException("Not correct input for query.");
    }

    private Query<Integer> parseNumberOfRoutesQuery(String input) throws QueryParseException {
        final FromToSettings fromToSettings = parseFromToForNumberOfRoutesQuery(input);

        // it is max stops query
        if (input.contains(NUMBER_OF_ROUTES_MAX_STOPS_START) && input.contains(NUMBER_OF_ROUTES_MAX_STOPS_END)) {
            return parseMaxStops(input, fromToSettings);

        // it is exact stops query
        } else if (input.contains(NUMBER_OF_ROUTES_EXACT_STOPS_START) && input.contains(NUMBER_OF_ROUTES_EXACT_STOPS_END)) {
            return parseExactStops(input, fromToSettings);

        // it is max distance query
        } else if (input.contains(NUMBER_OF_ROUTES_MAX_DISTANCE)) {
            return parseLessDistance(input, fromToSettings);

        // no query matches
        } else {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    /**
     * ==========================================================
     *                      Helper methods
     * ==========================================================
     */
    private class FromToSettings {
        private int fromIndex;
        private int toIndex;
        private String fromKeyWord;
        private String toKeyWord;
        private T from;
        private T to;
    }

    private String parseArgument(
            String input,
            FromToSettings fromToSettings,
            String fromKeyword,
            String endingString) throws QueryParseException {
        final int maxIndex = input.indexOf(fromKeyword, fromToSettings.toIndex);
        try {
            return input.substring(
                    maxIndex + fromKeyword.length() + 1,
                    input.indexOf(endingString, maxIndex + fromKeyword.length() + 1));
        } catch (Exception e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    private Query<Integer> parseMaxStops(String input, FromToSettings fromToSettings) throws QueryParseException {
        try {
            return new NumOfRoutesQuery<>(map, fromToSettings.from, fromToSettings.to, COUNT_PATHS_ALGORITHM)
                    .maxStops(Integer.parseInt(parseArgument(input, fromToSettings, NUMBER_OF_ROUTES_MAX_STOPS_START, " ")));
        } catch (Exception e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    private Query<Integer> parseExactStops(String input, FromToSettings fromToSettings) throws QueryParseException {
        try {
            return new NumOfRoutesQuery<>(map, fromToSettings.from, fromToSettings.to, COUNT_PATHS_ALGORITHM)
                    .exactStops(Integer.parseInt(parseArgument(input, fromToSettings, NUMBER_OF_ROUTES_EXACT_STOPS_START, " ")));
        } catch (Exception e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    private Query<Integer> parseLessDistance(String input, FromToSettings fromToSettings) throws QueryParseException {
        try {
        return new NumOfRoutesQuery<>(map, fromToSettings.from, fromToSettings.to, COUNT_PATHS_ALGORITHM)
                .maxDistance(Integer.parseInt(parseArgument(input, fromToSettings, NUMBER_OF_ROUTES_MAX_DISTANCE, ".")));
        } catch (Exception e) {
            throw new QueryParseException("Not correct input for query.");
        }
    }

    private FromToSettings parseFromToForNumberOfRoutesQuery(String input) throws QueryParseException {
        final FromToSettings fromToSettings = new FromToSettings();

        if (input.contains(NUMBER_OF_ROUTES_FROM) && input.contains(NUMBER_OF_ROUTES_TO)) {
            fromToSettings.fromIndex = input.indexOf(NUMBER_OF_ROUTES_FROM);
            fromToSettings.toIndex = input.indexOf(NUMBER_OF_ROUTES_TO, fromToSettings.fromIndex);
            fromToSettings.fromKeyWord = NUMBER_OF_ROUTES_FROM;
            fromToSettings.toKeyWord = NUMBER_OF_ROUTES_TO;
        } else if (input.contains(NUMBER_OF_ROUTES_STARTING) && input.contains(NUMBER_OF_ROUTES_ENDING)) {
            fromToSettings.fromIndex = input.indexOf(NUMBER_OF_ROUTES_STARTING);
            fromToSettings.toIndex = input.indexOf(NUMBER_OF_ROUTES_ENDING, fromToSettings.fromIndex);
            fromToSettings.fromKeyWord = NUMBER_OF_ROUTES_STARTING;
            fromToSettings.toKeyWord = NUMBER_OF_ROUTES_ENDING;
        } else {
            throw new QueryParseException("Not correct input for query.");
        }

        fromToSettings.from = getVertexFromStringAfterIndexAndKeyword(input, fromToSettings.fromIndex, fromToSettings.fromKeyWord);
        fromToSettings.to = getVertexFromStringAfterIndexAndKeyword(input, fromToSettings.toIndex, fromToSettings.toKeyWord);

        return fromToSettings;
    }

    // Return the correct type of Vertex
    protected abstract T getVertexFromStringAfterIndexAndKeyword(String input, int index, String keyword) throws QueryParseException;
    protected abstract T getVertexElementFromRouteVertexData(String routeVertexData)throws QueryParseException;
}
