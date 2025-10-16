package com.basejava.webapp;

public class MainDeadLock {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread1 пытается взять LOCK_2");
                synchronized (LOCK_2) {
                    System.out.println("Thread1");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread2 пытается взять LOCK_1");
                synchronized (LOCK_1) {
                    System.out.println("Thread2");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
