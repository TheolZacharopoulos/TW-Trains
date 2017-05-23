package graph.algorithms.route_distance;

import graph.Graph;
import graph.algorithms.GraphAlgorithm;

import java.util.LinkedList;
import java.util.Optional;

public interface RouteDistanceAlgorithm extends GraphAlgorithm {
    <T> Optional<Integer> findRouteDistance(Graph<T> graph, LinkedList<T> routeVertices);
}
