package graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EdgeTest {

    @Test
    public void testEquality_edge() {
        Vertex<Character> a = new Vertex<>('A');
        Vertex<Character> b = new Vertex<>('B');

        Edge<Character> e1 = new Edge<Character>(a, b);
        Edge<Character> e2 = new Edge<Character>(a, b);
        Edge<Character> e3 = new Edge<Character>(b, a);

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
    }

    @Test
    public void testEquality_weight_edge() {
        Vertex<Character> a = new Vertex<>('A');
        Vertex<Character> b = new Vertex<>('B');

        Edge<Character> e1 = new Edge<Character>(a, b, 3);
        Edge<Character> e2 = new Edge<Character>(a, b, 3);

        Edge<Character> e3 = new Edge<Character>(b, a, 1);
        Edge<Character> e4 = new Edge<Character>(a, b, 2);

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, e4);
    }

}