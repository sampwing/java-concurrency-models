package com.sampwing.concurrencymodels.mutexmemory;

public class Counting {

    public static int Count(int count, boolean withSynchronized) {
        class Counter {
            private int mCount = 0;

            public void increment() {
                ++mCount;
            }

            public int getCount() {
                return mCount;
            }
        }

        class SynchronizedCounter extends Counter {
            private int mCount = 0;

            @Override
            public synchronized void increment() {
                ++mCount;
            }

            public synchronized int getCount() {
                return mCount;
            }
        }

        final Counter counter;
        if (withSynchronized) {
            counter = new SynchronizedCounter();
        } else {
            counter = new Counter();
        }

        class CountingThread extends Thread {
            public void run() {
                for (int iteration = 0; iteration < count; ++iteration ) {
                   counter.increment();
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

        return counter.getCount();
    }
}
