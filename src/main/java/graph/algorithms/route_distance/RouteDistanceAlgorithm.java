package graph.algorithms.route_distance;

import graph.Graph;
import graph.GraphPath;
import graph.algorithms.GraphAlgorithm;
import graph.errors.GraphException;

/**
 * Represents an algorithm that can be used to calculate the distance of a path
 */
public interface RouteDistanceAlgorithm extends GraphAlgorithm {

    /**
     * Finds the total distance of a path and puts it back to the argument path
     * @param graph the graph
     * @param path the graph path
     * @param <T> the type of the graph elements
     * @throws GraphException in case something is wrong on the path
     */
    <T> void findRouteDistance(Graph<T> graph, GraphPath<T> path) throws GraphException;
}
