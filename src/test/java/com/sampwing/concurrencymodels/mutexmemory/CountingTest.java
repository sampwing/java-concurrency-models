package com.sampwing.concurrencymodels.mutexmemory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wings on 11/1/17.
 */
public class CountingTest {

    @Test
    public void testCountSynchronized() {
        int count = 1000;
        int expectedCount = count * 2;
        int result = Counting.Count(count, true);
        assertEquals(expectedCount, result);
    }

    @Test
    public void testCountUnsynchronized() {
        int count = 100000;
        int expectedCount = count * 2;
        int result = Counting.Count(count, false);
        // shouldn't be the same due to race condition
        assertNotEquals(expectedCount, result);
    }


}

