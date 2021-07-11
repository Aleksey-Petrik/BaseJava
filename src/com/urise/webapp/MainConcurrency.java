package com.urise.webapp;

public class MainConcurrency {
    private static int counter;
    //private static final Object LOCK = new Object();

    static class ThreadA extends Thread {
        private final A a;

        public ThreadA(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            System.out.println(a.getName());
        }
    }

    static class ThreadB extends Thread {
        private final B b;

        public ThreadB(B b) {
            this.b = b;
        }

        @Override
        public void run() {
            System.out.println(b.getName());
        }
    }

    static class A {
        private B b;

        public void setB(B b) {
            this.b = b;
        }

        public synchronized String getName() {
            return b.returnName();
        }

        private synchronized String returnName() {
            return "Class A";
        }
    }

    static class B {
        private A a;

        public void setA(A a) {
            this.a = a;
        }

        public synchronized String getName() {
            return a.returnName();
        }

        private synchronized String returnName() {
            return "Class B";
        }
    }


    public static void main(String[] args) throws InterruptedException {
        //---deadlock
        A a = new A();
        B b = new B();

        a.setB(b);
        b.setA(a);

        ThreadA threadA = new ThreadA(a);
        ThreadB threadB = new ThreadB(b);

        threadA.start();
        threadB.start();
        //----

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
