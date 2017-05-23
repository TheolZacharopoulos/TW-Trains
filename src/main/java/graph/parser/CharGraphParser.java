package graph.parser;

import graph.Graph;
import graph.errors.GraphException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharGraphParser extends GraphParser<String, Character> {

    public CharGraphParser(Graph<Character> graph) {
        super(graph);
    }

    @Override
    public Graph<Character> parse(String input) throws GraphParseException {
        final List<String> edgeDefinitions = new ArrayList<>();

        // detect input using regex
        edgeDefinitions.addAll(Arrays.asList(input.split("[\\s]*,[\\s]*")));

        // validate input
        if (!edgeDefinitions.stream().allMatch(this::isValidEdgeDefinition)) {
            throw new GraphParseException("Invalid edge definition.");
        }

        for (String edgeDefinition : edgeDefinitions) {
            try {
                graph.insertEdge(
                        edgeDefinition.charAt(0),
                        edgeDefinition.charAt(1),
                        Integer.parseInt(edgeDefinition.substring(2)));
            } catch (GraphException ex) {
                throw new GraphParseException("Error while constructing the graph.");
            }
        }

        return this.graph;
    }

    private boolean isValidEdgeDefinition(String edgeDef) {
        return edgeDef.length() == 3 &&
                Character.isAlphabetic(edgeDef.charAt(0)) &&
                Character.isAlphabetic(edgeDef.charAt(1)) &&
                edgeDef.substring(2).matches("\\d+");
    }
}
