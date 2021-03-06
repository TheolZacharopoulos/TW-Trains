package graph.adjacency_map;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import graph.errors.GraphException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of an undirected graph using an AdjacencyMap
 */
public class AdjacencyMapGraph<T> implements Graph<T> {
    protected Map<Vertex<T>, Map<Vertex<T>, Edge<T>>> outgoing = new HashMap<>();

    @Override
    public int numberOfVertices() {
        return outgoing.size();
    }

    @Override
    public int numberOfEdges() {
        int sum = 0;
        for (Vertex vertex : outgoing.keySet()) {
            sum += outgoing.get(vertex).size();
        }
        return sum;
    }

    @Override
    public Vertex<T> insertVertex(T v) {
        final Vertex<T> vertex = new Vertex<T>(v);
        outgoing.putIfAbsent(vertex, new HashMap<>());
        return vertex;
    }

    @Override
    public Edge<T> insertEdge(T from, T to, int weight) throws GraphException {
        final Edge<T> edge = createEdge(from, to, weight);
        outgoing.get(edge.getFrom()).putIfAbsent(edge.getTo(), edge);
        outgoing.get(edge.getTo()).putIfAbsent(edge.getFrom(), edge);

        return edge;
    }

    @Override
    public Set<Edge<T>> getEdges(Vertex<T> vertex) throws GraphException {
        checkVertex(vertex);

        final Map<Vertex<T>, Edge<T>> adjacencyMap = outgoing.get(vertex);
        return new HashSet<>(adjacencyMap.values());
    }

    @Override
    public Set<Vertex<T>> getVertices() {
        return outgoing.keySet();
    }

    @Override
    public Set<Edge<T>> getEdges() {
        return outgoing.values().stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<Vertex<T>> adjacent(Vertex<T> v) throws GraphException {
        return getEdges(v).stream()
                .map(Edge::getTo)
                .collect(Collectors.toSet());
    }

    public void checkVertex(Vertex<T> vertex) throws GraphException {
        if (!outgoing.containsKey(vertex)) {
            throw new GraphException("Vertex does not exist");
        }
    }

    protected Edge<T> createEdge(T from, T to, int weight) throws GraphException {
        final Vertex<T> vFrom = insertVertex(from);
        final Vertex<T> vTo = insertVertex(to);

        if (getEdge(vFrom, vTo).isPresent()) {
            throw new GraphException("Edge already exists.");
        }

        return new Edge<>(vFrom, vTo, weight);
    }

    protected Optional<Edge> getEdge(Vertex<T> from, Vertex<T> to) throws GraphException {
        checkVertex(from);
        checkVertex(to);
        return Optional.ofNullable(outgoing.get(from).get(to));
    }
}