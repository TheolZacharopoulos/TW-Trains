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
    private StopsDistanceSettings settings = new StopsDistanceSettings();

    // Set them to true since they are composed (predicate.and(...))
    private Predicate<GraphPath<T>> endPredicate = path -> true;
    private Predicate<GraphPath<T>> checkPathPredicate = path -> true;

    private final CountPathsAlgorithm algorithm;

    public NumOfRoutesQuery(RailwayMap<T> map, T from, T to, CountPathsAlgorithm algorithm) {
        super(map, from, to);
        this.algorithm = algorithm;
    }

    public NumOfRoutesQuery<T> maxStops(int stops) {
        this.settings.setMaxStops(stops);
        this.endPredicate = this.endPredicate.and(path -> path.getNumberOfStops() > stops);
        this.checkPathPredicate = this.checkPathPredicate.and(path -> to.equals(path.getLastVertex()));
        return this;
    }

    public NumOfRoutesQuery<T> exactStops(int stops) {
        this.settings.setExactStops(stops);
        this.endPredicate = this.endPredicate.and(path -> path.getNumberOfStops() > stops);
        this.checkPathPredicate = this.checkPathPredicate.and(path -> to.equals(path.getLastVertex()) && path.getNumberOfStops() == stops);
        return this;
    }

    public NumOfRoutesQuery<T> maxDistance(int distance) {
        this.settings.setMaxDistance(distance);
        this.endPredicate = this.endPredicate.and(path -> path.getTotalDistance() > distance);
        this.checkPathPredicate = this.checkPathPredicate.and(path -> to.equals(path.getLastVertex()) && path.getTotalDistance() < distance);
        return this;
    }

    @Override
    public Optional<Integer> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        super.execute();
        checkDistance();
        checkStops();

        try {
            final List<GraphPath<T>> paths = this.algorithm.getNumberOfPaths(
                    map.getGraph(),
                    from,
                    this.endPredicate,
                    this.checkPathPredicate);

            return Optional.of(paths.size());
        } catch (GraphException gE) {
            return Optional.empty();
        }
    }

    private void checkDistance() throws WrongQueryParameterValueException {
        if (settings.getMaxDistance() != null && settings.getMaxDistance() < 0) {
            throw new WrongQueryParameterValueException("Distance should be larger than 0");
        }
    }

    private void checkStops() throws WrongQueryParameterValueException {
        if ((settings.getExactStops() != null && settings.getExactStops() < 0) ||
            (settings.getMaxStops() != null && settings.getMaxStops() < 0))
        {
            throw new WrongQueryParameterValueException("Stops should be larger than 0");
        }
    }
}
