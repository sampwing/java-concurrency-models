package com.sampwing.concurrencymodels.mutexmemory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wings on 11/1/17.
 */
public class AtomicCounting {

    public static int Count(int count) {

        final AtomicInteger counter = new AtomicInteger();

        class CountingThread extends Thread {
            public void run() {
                for (int iteration = 0; iteration < count; ++iteration ) {
                   counter.incrementAndGet();
                }
            }
        }

        CountingThread ct1 = new CountingThread();
        CountingThread ct2 = new CountingThread();

        ct1.start(); ct2.start();
        try {
            ct1.join();
            ct2.join();
        } catch (InterruptedException ie) {
            return -1;
        }

        return counter.get();
    }
}
