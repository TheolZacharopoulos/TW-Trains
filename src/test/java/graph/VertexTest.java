package graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VertexTest {

    @Test
    public void testEquality() {
        Vertex<Character> a = new Vertex<>('A');
        Vertex<Character> b = new Vertex<>('B');
        Vertex<Character> a2 = new Vertex<>('A');

        assertNotEquals(a, b);
        assertEquals(a, a2);
    }

}