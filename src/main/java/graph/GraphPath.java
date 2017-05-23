package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a path on a graph
 * @param <T> the type of the Graph
 */
public class GraphPath<T> {
    private List<Vertex<T>> vertices;
    private int totalDistance;

    public GraphPath() {
        this(new LinkedList<>(), 0);
    }

    public GraphPath(List<Vertex<T>> vertices, int totalDistance) {
        this.vertices = vertices;
        this.totalDistance = totalDistance;
    }

    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void removeLast(int weight) {
        if (!this.vertices.isEmpty()) {
            totalDistance -= weight;
            this.vertices.remove(this.vertices.size() - 1);
        }
    }

    public void addVertexWithWeight(Vertex<T> node, int weight) {
        totalDistance += weight;
        vertices.add(node);
    }

    public Vertex<T> getLastVertex() {
        return !vertices.isEmpty()
            ? vertices.get(vertices.size() - 1)
            : null;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getNumberOfStops() {
        return Math.max(0, vertices.size() - 1);
    }
}