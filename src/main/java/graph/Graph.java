package graph;

import graph.errors.GraphException;

import java.util.Set;

/**
 * Represents a graph with elements of type T
 * @param <T> the type of the elements inside the graph
 */
public interface Graph<T> {

    /**
     * Get the number of vertices in the graph
     * @return the number of vertices
     */
    int numberOfVertices();

    /**
     * Get the number of edges in the graph
     * @return the number of edges
     */
    int numberOfEdges();

    /**
     * Insert a vertex in the graph
     * @param v the value of the new vertex
     * @return the new created vertex object
     */
    Vertex<T> insertVertex(T v);

    /**
     * Insert a new edge in the graph
     * @param from from which vertex the edges starts
     * @param to to which vertex the edges ends
     * @param weight the weight of the edge
     * @return the new created edge object.
     * @throws GraphException in case the edge already exists
     */
    Edge<T> insertEdge(T from, T to, int weight) throws GraphException;

    /**
     * Insert a new edge in the graph weight 1
     * @param from from which vertex the edges starts
     * @param to to which vertex the edges ends
     * @return the new created edge object.
     * @throws GraphException in case the edge already exists
     */
    default Edge<T> insertEdge(T from, T to) throws GraphException {
        return insertEdge(from, to, 1);
    }

    /**
     * Returns all the edges of the graph that with a source vertex
     * @param v the source vertex (object)
     * @return a set of edges
     * @throws GraphException in case no such vertex exists
     */
    Set<Edge<T>> getEdges(Vertex<T> v) throws GraphException;

    /**
     * Returns all the edges of the graph that with a source vertex
     * @param v the source vertex (value)
     * @return a set of edges
     * @throws GraphException in case no such vertex exists
     */
    default Set<Edge<T>> getEdges(T v) throws GraphException {
        return getEdges(new Vertex<T>(v));
    }

    /**
     * Returns all the vertices of the Graph
     * @return a set of vertices
     */
    Set<Vertex<T>> getVertices();

    /**
     * Returns all the edges of the Graph
     * @return a set of edges
     */
    Set<Edge<T>> getEdges();

    /**
     * Returns the adjacent vertices of a source vertex
     * @param v the source vertex (object)
     * @return a set of the adjacent vertices
     * @throws GraphException in case no such vertex exists
     */
    Set<Vertex<T>> adjacent(Vertex<T> v) throws GraphException;

    /**
     * Returns the adjacent vertices of a source vertex
     * @param v the source vertex (value)
     * @return a set of the adjacent vertices
     * @throws GraphException in case no such vertex exists
     */
    default Set<Vertex<T>> adjacent(T v) throws GraphException {
        return adjacent(new Vertex<T>(v));
    }

    /**
     * A helper method, it returns the weight between two vertices in the graph,
     * if they are directly connected through an edge.
     *
     * @param source a source vertex (from)
     * @param dest a destination vertex (to)
     * @return the weight between those vertices if they are directly connected through an edge
     * @throws GraphException in case no such vertex exists
     */
    default int getWeight(Vertex<T> source, Vertex<T> dest) throws GraphException {
        for (Edge<T> edge : this.getEdges(source)) {
            if (edge.getTo().equals(dest)) {
                return edge.getWeight();
            }
        }
        throw new GraphException("Should not go in here.");
    }
}
