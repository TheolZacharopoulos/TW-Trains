package railway.railway_query;

import graph.GraphPath;
import graph.Vertex;
import graph.algorithms.route_distance.RouteDistanceAlgorithm;
import graph.errors.GraphException;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.LinkedList;
import java.util.Optional;

public class RouteDistanceQuery<T> extends RailwayQuery<T, Integer> {
    private final LinkedList<Vertex<T>> route = new LinkedList<>();
    private final RouteDistanceAlgorithm algorithm;

    public RouteDistanceQuery(RailwayMap<T> map, RouteDistanceAlgorithm algorithm) {
        super(map);
        this.algorithm = algorithm;
    }

    public RouteDistanceQuery<T> addTownStop(T town) {
        final Vertex<T> vTown = new Vertex<>(town);
        if (!route.contains(vTown)) {
            route.add(vTown);
        }
        return this;
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        checkTownsNumber();
        final GraphPath<T> path = new GraphPath<T>(route, 0);

        try {
            algorithm.findRouteDistance(map.getGraph(), path);
        } catch (GraphException e) {
            return Optional.empty();
        }
        return Optional.of(path.getTotalDistance());
    }

    private void checkTownsNumber() throws MissingQueryParametersException {
        if (route.size() <= 1) {
            throw new MissingQueryParametersException("Number of stops on a route, should be greater than just 1.");
        }
    }
}
