package graph.algorithms;

import graph.algorithms.count_paths.CountPathsAlgorithm;
import graph.algorithms.count_paths.DFSCountPathsAlgorithm;
import graph.algorithms.route_distance.GreedyRouteDistanceAlgorithm;
import graph.algorithms.route_distance.RouteDistanceAlgorithm;
import graph.algorithms.shortest_path.DijkstraShortestPathAlgorithm;
import graph.algorithms.shortest_path.ShortestPathAlgorithm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GraphAlgorithmProviderTest {
    private GraphAlgorithmProvider algorithmProvider;

    @Before
    public void setup() {
        algorithmProvider = new GraphAlgorithmProvider();
    }

    @Test
    public void testShortestPathDijkstra() {
        ShortestPathAlgorithm algorithm = algorithmProvider.getShortestPathAlgorithm(DijkstraShortestPathAlgorithm.ALGORITHM_NAME);
        assertTrue(algorithm instanceof DijkstraShortestPathAlgorithm);
    }

    @Test
    public void testCountPathsDFS() {
        CountPathsAlgorithm algorithm = algorithmProvider.getCountPathsAlgorithm(DFSCountPathsAlgorithm.ALGORITHM_NAME);
        assertTrue(algorithm instanceof DFSCountPathsAlgorithm);
    }

    @Test
    public void testRoutesDistanceGreedy() {
        RouteDistanceAlgorithm algorithm = algorithmProvider.getRouteDistanceAlgorithm(GreedyRouteDistanceAlgorithm.ALGORITHM_NAME);
        assertTrue(algorithm instanceof GreedyRouteDistanceAlgorithm);
    }
}