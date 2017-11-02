package com.sampwing.concurrencymodels.mutexmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by wings on 11/1/17.
 */
public class ItemCount<Group, Item> {
    class Parser implements Runnable {
        private BlockingQueue<Group> mQueue;
        private Supplier<Iterable<Group>> mSupplier;

        public Parser(BlockingQueue<Group> queue, Supplier<Iterable<Group>> supplier) {
            mQueue = queue;
            mSupplier = supplier;
        }

        public void run() {
            try {
                Iterable<Group> groups = mSupplier.get();

                for (Group group: groups) {
                   mQueue.put(group);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Counter implements Runnable {

        private BlockingQueue<Group> mQueue;

        private Map<Item, Integer> mCounts;

        private Function<Group, Iterable<Item>> mFunction;

        public Counter(BlockingQueue<Group> queue, Map<Item, Integer> counts, Function<Group, Iterable<Item>> function) {
            mQueue = queue;
            mCounts = counts;
            mFunction = function;
        }

        private void countItem(Item item) {
            Integer currentCount = mCounts.get(item);
            if (currentCount == null) {
                mCounts.put(item, 1);
            } else {
                mCounts.put(item, currentCount + 1);
            }
        }

        public void run() {
            try {
               while (true) {
                   if (mQueue.peek() == null) {
                       // TODO should use a token here to indicate end of stream instead
                       Thread.sleep(1000);
                       break;
                   }

                   Group group = mQueue.take();

                   Iterable<Item> items = mFunction.apply(group);
                   for (Item item: items) {
                       countItem(item);
                   }
               }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    protected Map<Item, Integer> count(Supplier<Iterable<Group>> supplier, Function<Group, Iterable<Item>> function, int capacity) {
        ArrayBlockingQueue<Group> queue = new ArrayBlockingQueue<Group>(capacity);
        HashMap<Item, Integer> counts = new HashMap<>();

        Counter counter = new Counter(queue, counts, function);
        Parser parser = new Parser(queue, supplier);

//        Thread counterThread = new Thread(counter);
//        Thread parserThread = new Thread(parser);
//
//        counterThread.start();
//        parserThread.start();
//        try {
//            parserThread.join();
//            // queue.put(null);  // TODO fix this
//            counterThread.join();
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }
        parser.run();
        counter.run();

        return counts;
    }
}
