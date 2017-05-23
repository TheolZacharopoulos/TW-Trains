package graph;

import graph.errors.GraphException;

import java.util.Set;

/**
 * Represents a graph with elements of type T
 * @param <T> the type of the elements inside the graph
 */
public interface Graph<T> {
    int numberOfVertices();
    int numberOfEdges();

    Vertex<T> insertVertex(T v);

    Edge<T> insertEdge(T from, T to, int weight) throws GraphException;
    default Edge<T> insertEdge(T from, T to) throws GraphException {
        return insertEdge(from, to, 1);
    }

    Set<Edge<T>> getEdges(Vertex<T> v) throws GraphException;
    default Set<Edge<T>> getEdges(T v) throws GraphException {
        return getEdges(new Vertex<T>(v));
    }

    Set<Vertex<T>> getVertices();
    Set<Edge<T>> getEdges();

    Set<Vertex<T>> adjacent(Vertex<T> v) throws GraphException;
    default Set<Vertex<T>> adjacent(T v) throws GraphException {
        return adjacent(new Vertex<T>(v));
    }

    default int getWeight(Vertex<T> source, Vertex<T> dest) throws GraphException {
        for (Edge<T> edge : this.getEdges(source)) {
            if (edge.getTo().equals(dest)) {
                return edge.getWeight();
            }
        }
        throw new GraphException("Should not go in here.");
    }
}
