package graph.algorithms;

import graph.algorithms.count_paths.CountPathsAlgorithm;
import graph.algorithms.count_paths.DFSCountPathsAlgorithm;
import graph.algorithms.route_distance.BFSRouteDistanceAlgorithm;
import graph.algorithms.route_distance.RouteDistanceAlgorithm;
import graph.algorithms.shortest_path.DijkstraShortestPathAlgorithm;
import graph.algorithms.shortest_path.ShortestPathAlgorithm;

public class GraphAlgorithmProvider {
    private final static ShortestPathAlgorithm SHORTEST_PATH_ALGORITHM_DIJKSTRA = new DijkstraShortestPathAlgorithm();
    private final static CountPathsAlgorithm COUNT_PATHS_ALGORITHM_DFS = new DFSCountPathsAlgorithm();
    private final static RouteDistanceAlgorithm ROUTE_DISTANCE_ALGORITHM_BFS = new BFSRouteDistanceAlgorithm();

    public ShortestPathAlgorithm getShortestPathAlgorithm(String algorithmName) {
        if (algorithmName.equals(DijkstraShortestPathAlgorithm.ALGORITHM_NAME)) {
            return SHORTEST_PATH_ALGORITHM_DIJKSTRA;
        }
        return null;
    }

    public CountPathsAlgorithm getCountPathsAlgorithm(String algorithmName) {
        if (algorithmName.equals(DFSCountPathsAlgorithm.ALGORITHM_NAME)) {
            return COUNT_PATHS_ALGORITHM_DFS;
        }
        return null;
    }

    public RouteDistanceAlgorithm getRouteDistanceAlgorithm(String algorithmName) {
        if (algorithmName.equals(BFSRouteDistanceAlgorithm.ALGORITHM_NAME)) {
            return ROUTE_DISTANCE_ALGORITHM_BFS;
        }
        return null;
    }
}
