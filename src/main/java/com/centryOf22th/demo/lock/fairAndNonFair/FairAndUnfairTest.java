package com.centryOf22th.demo.lock.fairAndNonFair;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by louis on 16-11-18.
 */
public class FairAndUnfairTest {


    private static ReentrantLockExt fairLock = new ReentrantLockExt(true);
    private static ReentrantLockExt unfairLock = new ReentrantLockExt(false);


    private static class Job extends Thread {

        private ReentrantLockExt lock;

        public Job(ReentrantLockExt lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                lock.lock();
                System.out.println(i);

            }
            System.out.println(Thread.currentThread().getName());

            System.out.println("QueuedThreads:" + lock.getQueuedThreads());


        }
    }

    private static class ReentrantLockExt extends ReentrantLock {

        public ReentrantLockExt(boolean fair) {
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }

    public void testLock(ReentrantLockExt lock) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Job(lock);
        }
        for (int i = 0; i < 5; i++) {
            threads[i].start();
        }
    }

    public void unfair() {
        testLock(unfairLock);
    }

    public void fair() {
        testLock(fairLock);
    }

    public static void main(String[] args) {
        long start = System.nanoTime();

        new FairAndUnfairTest().unfair();
        System.out.println("end time " + (System.nanoTime() - start));
    }
}
