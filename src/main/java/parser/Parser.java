package parser;

/**
 * Represents a parser's interface
 * @param <I> The input type
 * @param <O> The output type
 */
public interface Parser<I, O> {

    /**
     * Parses an input and constructs an output out of it.
     * @param input the input to be parsed
     * @return the created object
     * @throws ParseException in case of input parsing error
     */
    O parse(I input) throws ParseException;
}
