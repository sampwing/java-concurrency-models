package com.sampwing.concurrencymodels.mutexmemory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wings on 11/1/17.
 */
public class ConcurrentSortedListTest {

    @Test
    public void testConcurrentSortedList() {
        Integer []values = {1, 2, 3, 4, 5};

        ConcurrentSortedList<Integer> csl = new ConcurrentSortedList<>();

        for (Integer value: values) {
            csl.insert(value);
        }
        assertEquals(csl.size(), values.length);

        for (Integer value: values) {
            assertTrue(csl.contains(value));
        }

        Integer []invalidValues = {6, 7, 8, 9, 10};

        for (Integer invalidValue: invalidValues) {
            assertFalse(csl.contains(invalidValue));
        }
    }

}

