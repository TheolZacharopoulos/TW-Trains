package graph.parser;

import graph.Graph;
import parser.Parser;

/**
 * An abstract Graph parsers
 * @param <I> the input type
 * @param <R> the Graph's elements type
 */
abstract public class GraphParser<I, R> implements Parser<I, Graph<R>> {

    protected Graph<R> graph;

    public GraphParser(Graph<R> graph) {
        this.graph = graph;
    }

    /**
     * Parses an input and constructs a Graph object out of it.
     * @param input the input to be parsed
     * @return the created Graph object
     * @throws GraphParseException in case of input parsing error
     */
    @Override
    abstract public Graph<R> parse(I input) throws GraphParseException;
}
