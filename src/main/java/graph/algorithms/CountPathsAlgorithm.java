package graph.algorithms;

import graph.Graph;
import graph.GraphPath;
import graph.Vertex;
import graph.errors.GraphException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CountPathsAlgorithm {

    public static <T> List<GraphPath<T>> getNumberOfPaths(
            Graph<T> graph,
            Vertex<T> source,
            Predicate<GraphPath<T>> endPredicate,
            Predicate<GraphPath<T>> checkPathPredicate) throws GraphException
    {
        final List<GraphPath<T>> paths = new ArrayList<>();

        for (Vertex<T> vertex : graph.adjacent(source)) {
            final GraphPath<T> path = new GraphPath<T>();
            path.addVertexWithWeight(source, 0);
            path.addVertexWithWeight(vertex, graph.getWeight(source, vertex));

            dfs(graph, vertex, endPredicate, checkPathPredicate, path, paths);
        }

        return paths;
    }

    private static <T> void dfs(
            Graph<T> graph,
            Vertex<T> source,
            Predicate<GraphPath<T>> endPredicate,
            Predicate<GraphPath<T>> checkPathPredicate,
            GraphPath<T> path,
            List<GraphPath<T>> paths) throws GraphException
    {
        if (checkPathPredicate.test(path)) {
            paths.add(path);
        }

        for (Vertex<T> vertex : graph.adjacent(source)) {
            path.addVertexWithWeight(vertex, graph.getWeight(source, vertex));

            if (endPredicate.test(path)) {
                path.removeLast(graph.getWeight(source, vertex));
                continue;
            } else {
                dfs(graph, vertex, endPredicate, checkPathPredicate, path, paths);
            }

            path.removeLast(graph.getWeight(source, vertex));
        }
    }
}
