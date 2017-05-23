package railway.railway_query;

import graph.algorithms.shortest_path.ShortestPathAlgorithm;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.Optional;

public class ShortestRouteQuery<T> extends FromToRailwayQuery<T, Integer> {
    private ShortestPathAlgorithm algorithm;

    public ShortestRouteQuery(RailwayMap<T> map, ShortestPathAlgorithm algorithm) {
        super(map);
        this.algorithm = algorithm;
    }

    public ShortestRouteQuery(RailwayMap<T> map, T from, T to, ShortestPathAlgorithm algorithm) {
        super(map, from, to);
        this.algorithm = algorithm;
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        super.execute();
        return this.algorithm.findShortestPath(map.getGraph(), from, to);
    }
}
