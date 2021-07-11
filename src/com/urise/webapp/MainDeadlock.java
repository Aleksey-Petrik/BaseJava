package com.urise.webapp;

public class MainDeadlock {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        thread(a, b);
        thread(b, a);
    }

    private static void thread(AB obj1, AB obj2) {
        new Thread(() -> {
            synchronized (obj1) {
                System.out.println(obj1.getName());
                synchronized (obj2){
                    System.out.println(obj2.getName());
                }
            }
        }).start();
    }

    interface AB {
        String getName();
    }

    static class A implements AB {
        public String getName() {
            return "Class A";
        }
    }

    static class B implements AB {
        public String getName() {
            return "Class B";
        }
    }
}
