package com.sampwing.concurrencymodels.mutexmemory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wings on 11/1/17.
 */
public class ConcurrentSortedList<T extends Comparable<T>> {
    private class Node {
        T mValue;
        Node mPrevious;
        Node mNext;
        ReentrantLock mLock = new ReentrantLock();

        Node() {}

        Node(T value, Node previous, Node next) {
            mValue = value;
            mPrevious = previous;
            mNext = next;
        }

        private int compareTo(T value) {
            return mValue.compareTo(value);
        }
    }

    private final Node mHead;
    private final Node mTail;

    public ConcurrentSortedList() {
        mHead = new Node();
        mTail = new Node();
        mHead.mNext = mTail;
        mTail.mPrevious = mHead;
    }

    public int size() {
        Node current = mHead;
        int count = 0;

        while (current.mNext != mTail) {
            ReentrantLock lock = current.mLock;
            lock.lock();

            try {
                ++count;
                current = current.mNext;
            } finally {
                lock.unlock();
            }

        }
        return count;
    }

    public boolean contains(T value) {
        Node current = mHead;

        while (current != null) {
            ReentrantLock lock = current.mLock;
            lock.lock();

            try {
                if (value == current.mValue) {
                    return true;
                }
                current = current.mNext;
            } finally {
               lock.unlock();
            }
        }

        return false;
    }


    public void insert(T value) {
        Node current = mHead;
        current.mLock.lock();

        Node next = current.mNext;
        try {
            while (true) {
                next.mLock.lock();
                try {
                    if (next == mTail || next.compareTo(value) < 0) {
                        Node node = new Node(value, current, next);
                        next.mPrevious = node;
                        current.mNext = node;
                        return;
                    }
                } finally {
                    current.mLock.unlock();
                }
                current = next;
                next = current.mNext;
            }
        } finally {
            next.mLock.unlock();
        }
    }
}
