package railway.railway_query;

import graph.algorithms.RouteDistanceAlgorithm;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.LinkedList;
import java.util.Optional;

public class RouteDistanceQuery<T> extends RailwayQuery<T, Integer> {
    private final LinkedList<T> route = new LinkedList<>();

    public RouteDistanceQuery(RailwayMap<T> map) {
        super(map);
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
        return RouteDistanceAlgorithm.findRouteDistance(this.map.getGraph(), route);
    }

    private void checkTownsNumber() throws MissingQueryParametersException {
        if (route.size() <= 1) {
            throw new MissingQueryParametersException("Number of stops on a route, should be greater than just 1.");
        }
    }
}
