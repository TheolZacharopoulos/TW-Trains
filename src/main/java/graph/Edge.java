package graph;

/**
 * Represents an edge between two vertices inside a graph.
 * @param <T> the type of the elements of the graph.
 */
public class Edge<T> {
    protected final Vertex<T> from;
    protected final Vertex<T> to;
    private final int weight;

    public Edge(Vertex<T> from, Vertex<T> to) {
        this(from, to, 1);
    }

    public Edge(Vertex<T> from, Vertex<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex<T> getFrom() {
        return from;
    }

    public Vertex<T> getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Edge) &&
                getFrom().equals(((Edge)obj).getFrom()) &&
                getTo().equals(((Edge)obj).getTo()) &&
                getWeight() == (((Edge)obj).getWeight());
    }

    @Override
    public String toString() {
        return (new StringBuilder()
                .append(getFrom())
                .append("-(")
                .append(getWeight())
                .append(")->")
                .append(getTo()))
                .toString();
    }

    @Override
    public int hashCode() {
        return getFrom().hashCode() + getTo().hashCode() + getWeight();
    }
}