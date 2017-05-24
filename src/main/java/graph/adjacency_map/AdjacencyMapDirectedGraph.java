package graph.adjacency_map;

import graph.Edge;
import graph.Vertex;
import graph.errors.GraphException;

import java.util.*;

/**
 * An implementation of a directed graph using an AdjacencyMap
 */
public class AdjacencyMapDirectedGraph<T> extends AdjacencyMapGraph<T> {
    protected Map<Vertex<T>, Map<Vertex<T>, Edge<T>>> incoming = new HashMap<>();

    public Vertex<T> insertVertex(T v) {
        final Vertex<T> vertex = super.insertVertex(v);
        incoming.put(vertex, new HashMap<>());
        return vertex;
    }

    @Override
    public Edge<T> insertEdge(T from, T to, int weight) throws GraphException {
        final Edge<T> edge = createEdge(from, to, weight);

        outgoing.get(edge.getFrom()).putIfAbsent(edge.getTo(), edge);

        incoming.putIfAbsent(edge.getTo(), new HashMap<>());
        incoming.get(edge.getTo()).putIfAbsent(edge.getFrom(), edge);

        return edge;
    }
}