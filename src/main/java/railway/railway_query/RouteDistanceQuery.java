package railway.railway_query;

import graph.algorithms.route_distance.RouteDistanceAlgorithm;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.LinkedList;
import java.util.Optional;

public class RouteDistanceQuery<T> extends RailwayQuery<T, Integer> {
    private final LinkedList<T> route = new LinkedList<>();
    private final RouteDistanceAlgorithm algorithm;

    public RouteDistanceQuery(RailwayMap<T> map, RouteDistanceAlgorithm algorithm) {
        super(map);
        this.algorithm = algorithm;
    }

    public RouteDistanceQuery<T> addTownStop(T town) {
        if (!route.contains(town)) {
            route.add(town);
        }
        return this;
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        checkTownsNumber();
        return this.algorithm.findRouteDistance(this.map.getGraph(), route);
    }

    private void checkTownsNumber() throws MissingQueryParametersException {
        if (route.size() <= 1) {
            throw new MissingQueryParametersException("Number of stops on a route, should be greater than just 1.");
        }
    }
}
