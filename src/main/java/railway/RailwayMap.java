package railway;

import graph.Graph;

/**
 * Represents a railway map,
 * contains a graph which describes towns a routes to them.
 */
public class RailwayMap<T> {
    private final String land;
    private final Graph<T> graph;

    public RailwayMap(String land, Graph<T> graph) {
        this.land = land;
        this.graph = graph;
    }

    public Graph<T> getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return land;
    }
}
