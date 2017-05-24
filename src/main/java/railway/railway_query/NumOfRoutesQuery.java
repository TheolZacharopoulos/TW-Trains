package railway.railway_query;

import graph.GraphPath;
import graph.algorithms.count_paths.CountPathsAlgorithm;
import graph.errors.GraphException;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class NumOfRoutesQuery<T> extends FromToRailwayQuery<T, Integer> {

    private Integer maxStops = null;
    private Integer exactStops = null;
    private Integer maxDistance = null;

    // Set them to true since they are composed (predicate.and(...))
    private Predicate<GraphPath<T>> endPredicate = path -> true;
    private Predicate<GraphPath<T>> checkPathPredicate = path -> true;

    private final CountPathsAlgorithm algorithm;

    public NumOfRoutesQuery(RailwayMap<T> map, T from, T to, CountPathsAlgorithm algorithm) {
        super(map, from, to);
        this.algorithm = algorithm;
    }

    public NumOfRoutesQuery<T> maxStops(int stops) {
        maxStops = stops;
        endPredicate = endPredicate.and(path -> path.getNumberOfStops() > stops);
        checkPathPredicate = checkPathPredicate.and(path -> to.equals(path.getLastVertex()));
        return this;
    }

    public NumOfRoutesQuery<T> exactStops(int stops) {
        exactStops = stops;
        endPredicate = endPredicate.and(path -> path.getNumberOfStops() > stops);
        checkPathPredicate = checkPathPredicate.and(path -> to.equals(path.getLastVertex()) && path.getNumberOfStops() == stops);
        return this;
    }

    public NumOfRoutesQuery<T> maxDistance(int distance) {
        maxDistance = distance;
        endPredicate = endPredicate.and(path -> path.getTotalDistance() > distance);
        checkPathPredicate = checkPathPredicate.and(path -> to.equals(path.getLastVertex()) && path.getTotalDistance() < distance);
        return this;
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        super.execute();
        checkDistance();
        checkStops();

        try {
            final List<GraphPath<T>> paths = algorithm.getNumberOfPaths(
                    map.getGraph(), from, endPredicate, checkPathPredicate);

            return Optional.of(paths.size());
        } catch (GraphException gE) {
            return Optional.empty();
        }
    }

    private void checkDistance() throws WrongQueryParameterValueException {
        if (maxDistance != null && maxDistance < 0) {
            throw new WrongQueryParameterValueException("Distance should be larger than 0");
        }
    }

    private void checkStops() throws WrongQueryParameterValueException {
        if ((exactStops != null && exactStops < 0) || (maxStops != null && maxStops < 0)) {
            throw new WrongQueryParameterValueException("Stops should be larger than 0");
        }
    }
}
