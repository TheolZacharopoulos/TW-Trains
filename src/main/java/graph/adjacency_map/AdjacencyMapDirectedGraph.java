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
        this.incoming.put(vertex, new HashMap<>());
        return vertex;
    }

    @Override
    public Edge<T> insertEdge(T from, T to, int weight) throws GraphException {
        final Vertex<T> vFrom = insertVertex(from);
        final Vertex<T> vTo = insertVertex(to);

        if (this.getEdge(vFrom, vTo).isPresent()) {
            throw new GraphException("Edge already exists.");
        }

        final Edge<T> edge = new Edge<>(vFrom, vTo, weight);

        this.outgoing.get(vFrom).putIfAbsent(vTo, edge);

        this.incoming.putIfAbsent(vTo, new HashMap<>());
        this.incoming.get(vTo).putIfAbsent(vFrom, edge);

        return edge;
    }
}