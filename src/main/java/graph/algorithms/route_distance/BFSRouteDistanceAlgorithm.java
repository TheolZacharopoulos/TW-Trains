package graph.algorithms.route_distance;

import graph.Edge;
import graph.Graph;
import graph.GraphPath;
import graph.Vertex;
import graph.errors.GraphException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A Breadth First Search implementation
 */
public class BFSRouteDistanceAlgorithm implements RouteDistanceAlgorithm {
    public static final String ALGORITHM_NAME = "BFS_ROUTE_DISTANCE";

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }

    public <T> void findRouteDistance(Graph<T> graph, GraphPath<T> path) throws GraphException {
        Integer totalDistance = 0;
        final List<T> routeVertices = path.getVertices().stream().map(Vertex::getElement).collect(Collectors.toList());

        // keep the previous vertex and start for the second
        // in order to have both previous and next
        // this way we construct the path edge by edge.
        T prev = routeVertices.get(0);
        for (int i = 0;i < routeVertices.size() - 1; i++) {
            final T next = routeVertices.get(i + 1);

            boolean found = false;
            for (Edge<T> edge : graph.getEdges(prev)) {
                if (edge.getTo().getElement().equals(next)) {
                    found = true;
                    totalDistance += edge.getWeight();
                }
            }

            // in the case there is no neighbour then there is no route.
            if (!found) {
                throw new GraphException("No such path.");
            }

            prev = next;
        }

        path.setTotalDistance(totalDistance);
    }
}
