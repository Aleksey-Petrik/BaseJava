package com.urise.webapp;

public class MainConcurrency {
    private static int counter;
    //private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());//имя главного потока

        Thread thread0 = new Thread() {//анонимный класс который наследуется от Thread
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());//имя потока
            }
        };
        thread0.start();

        new Thread() {//анонимный класс который наследуется от Thread
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());//имя потока
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
            }
        }).start();
        //lambda
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();

        System.out.println(thread0.getName() + " " + thread0.getState());

        for (int i = 0; i < 10_000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            });
            thread.start();
            //thread.join();//выполнять потоки последовательно, не одновременно
        }
        Thread.sleep(800);
        System.out.println(counter);
    }

    private static void inc() {
        double a = Math.sin(13.);
        //synchronized (LOCK) {
        synchronized (MainConcurrency.class) {
            counter++;
        }
    }
}
