package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeTest {
    static Range r1;
    static Range r2;
    static Range r3;
    static Range r4;

    @BeforeEach
    void setUp() {
        r1 = new Range();
        r1.setLowerBound(5);
        r1.setUpperBound(10);
        r2 = new Range();
        r2.setLowerBound(12);
        r2.setUpperBound(24);
        r3 = new Range();
        r3.setLowerBound(17);
        r3.setUpperBound(26);
        r4 = new Range();
        r4.setLowerBound(6);
        r4.setUpperBound(9);

    }

    @Test
    void isBefore() {
        assertTrue(r1.isBefore(r2));
        assertFalse(r2.isBefore(r1));
    }

    @Test
    void isAfter() {
        assertFalse(r1.isAfter(r2));
        assertTrue(r2.isAfter(r1));
    }

    @Test
    void isConcurrent() {
        assertTrue(r2.isConcurrent(r3));
        assertTrue(r3.isConcurrent(r2));
    }


    @ParameterizedTest
    @ValueSource(longs = {13, 15, 20})
    void contains(long value) {
        assertTrue(r2.contains(value));
    }

    @Test
    void asList() {
        List<Long> list = r1.asList();
        assertTrue(!list.isEmpty());
        for (long i = r1.getLowerBound(); i <= r1.getUpperBound(); i++) {
            assertTrue(list.contains(i));
        }
    }

    @Test
    void asIterator() {
        Iterator<Long> iterator = r1.asIterator();
        assertTrue(!r1.asList().isEmpty());
        assertTrue(iterator.hasNext());
        assertEquals(r1.asList().get(0), iterator.next());
    }

    @Test
    void getLowerBound() {
        assertEquals(5, r1.getLowerBound());
        assertEquals(12, r2.getLowerBound());
        assertEquals(17, r3.getLowerBound());
        assertEquals(6, r4.getLowerBound());
    }

    @Test
    void getUpperBound() {
        assertEquals(10, r1.getUpperBound());
        assertEquals(24, r2.getUpperBound());
        assertEquals(26, r3.getUpperBound());
        assertEquals(9, r4.getUpperBound());

    }

    @Test
    void equals() {
        Range r5 = new Range();
        r5.setUpperBound(10);
        r5.setLowerBound(5);
        assertTrue(r1.equals(r5) && r5.equals(r1));

    }
}