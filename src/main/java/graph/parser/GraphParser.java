package graph.parser;

import graph.Graph;
import parser.Parser;

abstract public class GraphParser<I, O> implements Parser<I, Graph<O>> {

    protected Graph<O> graph;

    public GraphParser(Graph<O> graph) {
        this.graph = graph;
    }

    @Override
    abstract public Graph<O> parse(I input) throws GraphParseException;
}
