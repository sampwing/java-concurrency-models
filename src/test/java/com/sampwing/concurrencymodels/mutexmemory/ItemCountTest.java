package com.sampwing.concurrencymodels.mutexmemory;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by wings on 11/1/17.
 */
public class ItemCountTest {

    @Test
    public void testWordCount() {
        String sentences = "this is a sentence. just like this one. and this one.";

        Supplier<Iterable<String>> supplier = () -> (Arrays.asList(sentences.split(".")));
        Function<String, Iterable<String>> tokenizer =
                (sentence) -> (Arrays.asList(sentence.split("\\s")));

        Map<String, Integer> counts = new ItemCount<String, String>().count(supplier, tokenizer, 100);

        Integer expectedThisCount = 3;
        Integer thisCount = counts.get("this");

        assertEquals(expectedThisCount, thisCount);

    }

}

