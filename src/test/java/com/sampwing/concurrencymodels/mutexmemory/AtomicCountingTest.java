package com.sampwing.concurrencymodels.mutexmemory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by wings on 11/1/17.
 */
public class AtomicCountingTest {

    @Test
    public void testCount() {
        int count = 1000;
        int expectedCount = count * 2;
        int result = AtomicCounting.Count(count);
        assertEquals(expectedCount, result);
    }

}

