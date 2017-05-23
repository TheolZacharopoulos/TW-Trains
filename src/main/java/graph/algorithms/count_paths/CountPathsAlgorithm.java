package graph.algorithms.count_paths;

import graph.Graph;
import graph.GraphPath;
import graph.Vertex;
import graph.algorithms.GraphAlgorithm;
import graph.errors.GraphException;

import java.util.List;
import java.util.function.Predicate;

public interface CountPathsAlgorithm extends GraphAlgorithm {
    <T> List<GraphPath<T>> getNumberOfPaths(
        Graph<T> graph,
        Vertex<T> source,
        Predicate<GraphPath<T>> endPredicate,
        Predicate<GraphPath<T>> checkPathPredicate) throws GraphException;
}
