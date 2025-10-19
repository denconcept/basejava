package com.basejava.webapp;

public class MainDeadLock {

    static final String LOCK_0 = "lock0";
    static final String LOCK_1 = "lock1";

    public static void main(String[] args) {
        deadLock(LOCK_0, LOCK_1);
        deadLock(LOCK_1, LOCK_0);
    }

    private static void deadLock(String lock0, String lock1) {
        new Thread(() -> {
            System.out.println("Waiting " + lock0);
            synchronized (lock0) {
                System.out.println("Holding " + lock0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Waiting " + lock1);
                synchronized (lock1) {
                    System.out.println("Holding " + lock1);
                }
            }
        }).start();
    }
}
