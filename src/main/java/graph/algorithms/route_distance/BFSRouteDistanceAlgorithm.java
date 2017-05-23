package graph.algorithms.route_distance;

import graph.Edge;
import graph.Graph;
import graph.errors.GraphException;

import java.util.LinkedList;
import java.util.Optional;

public class BFSRouteDistanceAlgorithm implements RouteDistanceAlgorithm {
    public static final String ALGORITHM_NAME = "BFS_ROUTE_DISTANCE";

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }

    public <T> Optional<Integer> findRouteDistance(Graph<T> graph, LinkedList<T> routeVertices) {
        Integer totalDistance = 0;

        // keep the previous vertex and start for the second
        // in order to have both previous and next
        // this way we construct the path edge by edge.
        T prev = routeVertices.get(0);
        for (int i = 0;i < routeVertices.size() - 1; i++) {
            final T next = routeVertices.get(i + 1);

            boolean found = false;
            try {
                for (Edge<T> edge : graph.getEdges(prev)) {
                    if (edge.getTo().getElement().equals(next)) {
                        found = true;
                        totalDistance += edge.getWeight();
                    }
                }
            } catch (GraphException gE) {
                return Optional.empty();
            }

            // in the case there is no neighbour then there is no route.
            if (!found) {
                return Optional.empty();
            }

            prev = next;
        }
        return Optional.ofNullable(totalDistance);
    }
}
