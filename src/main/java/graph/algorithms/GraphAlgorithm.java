package graph.algorithms;

/**
 * Represents an algorithm for the Graph object
 */
public interface GraphAlgorithm {

    /**
     * Returns the name of the algorithm in order to identify on the strategy / factory pattern
     * @return the name of the algorithm
     */
    String getAlgorithmName();
}
