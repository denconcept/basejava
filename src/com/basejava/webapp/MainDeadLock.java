package com.basejava.webapp;

public class MainDeadLock {

    private static final Object LOCK_0 = new Object() {
        @Override
        public String toString() {
            return "lock0";
        }
    };
    private static final Object LOCK_1 = new Object() {
        @Override
        public String toString() {
            return "lock1";
        }
    };

    public static void main(String[] args) {
        Thread thread0 = new Thread(() -> callDeadLock(LOCK_0, LOCK_1));
        Thread thread1 = new Thread(() -> callDeadLock(LOCK_1, LOCK_0));

        thread0.start();
        thread1.start();
    }

    private static void callDeadLock(Object lock0, Object lock1) {
        synchronized (lock0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " пытается взять " + lock1.toString());
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}
