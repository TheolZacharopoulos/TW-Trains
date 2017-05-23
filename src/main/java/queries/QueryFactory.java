package queries;

/**
 * Builds queries based on a set of instructions
 * @param <R> The type of the query's result
 */
public interface QueryFactory<R> {

    /**
     * Build a query base on the query instructions
     * @param instructions the query instructions
     * @return a query
     */
    Query<R> getQuery(String instructions);
}
