package graph;

public class Vertex<T> {
    private final T element;

    public Vertex(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Vertex) &&
            getElement().equals(((Vertex)obj).getElement());
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public int hashCode() {
        return element.hashCode();
    }
}