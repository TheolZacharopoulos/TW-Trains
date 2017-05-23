package graph.algorithms.shortest_path;

import graph.Graph;
import graph.Vertex;
import graph.algorithms.GraphAlgorithm;

import java.util.Optional;

public interface ShortestPathAlgorithm extends GraphAlgorithm {
    <T> Optional<Integer> findShortestPath(Graph<T> graph, Vertex<T> start, Vertex<T> end);
}
