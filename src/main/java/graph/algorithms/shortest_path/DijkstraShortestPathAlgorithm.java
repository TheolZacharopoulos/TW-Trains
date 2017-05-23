package graph.algorithms.shortest_path;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import graph.errors.GraphException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A dijkstra's algorithm implementation
 */
public class DijkstraShortestPathAlgorithm implements ShortestPathAlgorithm {
    public static final String ALGORITHM_NAME = "DIJKSTRA_SHORTEST_PATH";

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }

    /**
     * Helper class used for saving temporary data while traversing
     */
    private class VertexInfo {
        private int distance;
        private boolean visited;

        VertexInfo(int dist, boolean visited) {
            this.distance = dist;
            this.visited = visited;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    private <T> Vertex<T> getClosestNeighbour(Map<Vertex<T>, VertexInfo> info) {
        Vertex<T> closest = null;
        int minDistance = 0;

        for (Vertex<T> vertex : info.keySet()) {
            if (!info.get(vertex).isVisited()) {
                if (closest == null || info.get(vertex).getDistance() < minDistance) {
                    closest = vertex;
                    minDistance = info.get(vertex).getDistance();
                }
            }
        }
        return closest;
    }

    @Override
    public <T> Optional<Integer> findShortestPath(Graph<T> graph, Vertex<T> start, Vertex<T> end) {
        final Map<Vertex<T>, VertexInfo> info = new HashMap<>();
        info.put(start, new VertexInfo(0, false));

        try {
            Vertex<T> cur = start;
            do {
                for (Edge<T> edge : graph.getEdges(cur)) {
                    final Vertex<T> neighbor = edge.getTo();
                    final int distance = info.get(cur).getDistance() + edge.getWeight();

                    if (info.get(neighbor) == null) {
                        info.put(neighbor, new VertexInfo(distance, false));

                        // shorter path is found
                    } else if (info.get(neighbor).getDistance() > distance) {
                        info.get(neighbor).setDistance(distance);
                    }
                }

                info.get(cur).setVisited(true);

                // this is in case start == end,
                // where we do not want to get 0 but a new path starting from the next one.
                if (cur.equals(end)) {
                    info.remove(cur);
                }

                cur = getClosestNeighbour(info);

                if (cur == null) {
                    break;
                }
            } while (!cur.equals(end));
        } catch (GraphException gE) {
            return Optional.empty();
        }

        if (info.get(end) == null) {
            return Optional.empty();
        }
        return Optional.of(info.get(end).getDistance());
    }
}
