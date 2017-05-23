package graph.parser;

import graph.Graph;
import parser.Parser;

/**
 * An abstract Graph parsers
 * @param <I> the input type
 * @param <O> the Graph's elements type
 */
abstract public class GraphParser<I, O> implements Parser<I, Graph<O>> {

    protected Graph<O> graph;

    public GraphParser(Graph<O> graph) {
        this.graph = graph;
    }

    /**
     * Parses an input and constructs a Graph object out of it.
     * @param input the input to be parsed
     * @return the created Graph object
     * @throws GraphParseException in case of input parsing error
     */
    @Override
    abstract public Graph<O> parse(I input) throws GraphParseException;
}
