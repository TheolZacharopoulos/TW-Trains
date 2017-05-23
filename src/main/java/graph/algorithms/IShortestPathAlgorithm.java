package graph.algorithms;

import graph.Graph;
import graph.Vertex;

import java.util.Optional;

public interface IShortestPathAlgorithm {
    <T> Optional<Integer> findShortestPath(Graph<T> graph, Vertex<T> start, Vertex<T> end);
}
