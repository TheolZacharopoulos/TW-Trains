package queries;

/**
 * Builds queries based on a set of instructions
 */
public interface QueryFactory<R> {
    Query<R> getQuery(String instructions);
}
