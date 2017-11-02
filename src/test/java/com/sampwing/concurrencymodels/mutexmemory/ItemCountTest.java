package com.sampwing.concurrencymodels.mutexmemory;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by wings on 11/1/17.
 */
public class ItemCountTest {

    @Test
    public void testWordCount() {
        String sentences = "this is a sentence. just like this one. and this one.";
        Function<String, Iterable<String>> stringSplitter = new Function<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(String s) {
                return Arrays.asList(s.split("."));
            }
        };
        Function<String, Iterable<String>> stringSplitter = new Function<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(String s) {
                return Arrays.asList(s.split("."));
            }
        };

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

