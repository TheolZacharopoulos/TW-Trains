package graph.algorithms.count_paths;

import graph.Graph;
import graph.GraphPath;
import graph.Vertex;
import graph.algorithms.GraphAlgorithm;
import graph.errors.GraphException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents an algorithm that can be used to count paths
 */
public interface CountPathsAlgorithm extends GraphAlgorithm {

    /**
     * Returns the number of paths from a given vertex to a predicate (endPredicate).
     *
     * @param graph the graph
     * @param source the source vertex
     * @param endPredicate a predicate that will determine the end
     * @param checkPathPredicate a predicate that will determine if a path is proper
     * @param <T> the type of the Graph's elements
     * @return a list of graph paths
     * @throws GraphException in case something is wrong
     */
    <T> List<GraphPath<T>> getNumberOfPaths(
        Graph<T> graph,
        Vertex<T> source,
        Predicate<GraphPath<T>> endPredicate,
        Predicate<GraphPath<T>> checkPathPredicate) throws GraphException;
}
