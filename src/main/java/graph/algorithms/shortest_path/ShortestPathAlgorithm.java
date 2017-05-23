package graph.algorithms.shortest_path;

import graph.Graph;
import graph.Vertex;
import graph.algorithms.GraphAlgorithm;

import java.util.Optional;

/**
 * Represents an algorithm that can be used to to find the shortest's path distance
 */
public interface ShortestPathAlgorithm extends GraphAlgorithm {

    /**
     * Returns the shortest's path distance
     * @param graph the graph
     * @param start the vertex to start from
     * @param end the vertex to end to
     * @param <T> the type of the graph's elements
     * @return an optional distance, empty in case path is not found
     */
    <T> Optional<Integer> findShortestPath(Graph<T> graph, Vertex<T> start, Vertex<T> end);
}
