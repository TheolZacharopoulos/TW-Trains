package railway.railway_query;

import queries.Query;
import railway.RailwayMap;

abstract public class RailwayQuery<T, R> implements Query<R> {
    protected final RailwayMap<T> map;

    public RailwayQuery(RailwayMap<T> map) {
        this.map = map;
    }
}
