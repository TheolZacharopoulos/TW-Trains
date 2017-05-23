package railway.railway_query;

import graph.Vertex;
import queries.errors.MissingQueryParametersException;
import queries.errors.WrongQueryParameterValueException;
import railway.RailwayMap;

import java.util.Optional;

public abstract class FromToRailwayQuery<T, R> extends RailwayQuery <T, R> {
    protected Vertex<T> from;
    protected Vertex<T> to;

    public FromToRailwayQuery(RailwayMap<T> map) {
        super(map);
    }

    public FromToRailwayQuery(RailwayMap<T> map, T from, T to) {
        super(map);
        this.from = new Vertex<>(from);
        this.to = new Vertex<>(to);
    }

    public FromToRailwayQuery<T, R> from(T from) {
        this.from = new Vertex<>(from);
        return this;
    }

    public FromToRailwayQuery<T, R> to(T to) {
        this.to = new Vertex<>(to);
        return this;
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
