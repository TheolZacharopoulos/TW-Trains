package railway.railway_query;

import graph.Vertex;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.Optional;

public abstract class FromToRailwayQuery<T, R> extends RailwayQuery <T, R> {
    protected Vertex<T> from;
    protected Vertex<T> to;

    public FromToRailwayQuery(RailwayMap<T> map, T from, T to) {
        super(map);

        if (from != null) {
            this.from = new Vertex<>(from);
        }

        if (to != null) {
            this.to = new Vertex<>(to);
        }
    }

    @Override
    public Optional<R> execute() throws MissingQueryParametersException, WrongQueryParameterValueException {
        checkFromTo();
        return Optional.empty();
    }

    private void checkFromTo() throws MissingQueryParametersException {
        if (this.from == null || this.to == null) {
            throw new MissingQueryParametersException("From and To parameters should exist");
        }
    }
}
