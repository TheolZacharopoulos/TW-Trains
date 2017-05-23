package railway.railway_query;

import graph.algorithms.ShortestPathAlgorithm;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.Optional;

public class ShortestRouteQuery<T> extends FromToRailwayQuery<T, Integer> {
    public ShortestRouteQuery(RailwayMap<T> map) {
        super(map);
    }

    public ShortestRouteQuery(RailwayMap<T> map, T from, T to) {
        super(map, from, to);
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        super.execute();
        return ShortestPathAlgorithm.findShortestPath(map.getGraph(), from, to);
    }
}
