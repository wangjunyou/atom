package com.wjy.core;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MeMo {
    static class Dd {
        String a;
        String b;
    }
    public static void main(String[] args) throws Exception {
        /*for (int i = 0; i < 10; i++) {
            DeMo2 d = new DeMo2(""+i);
            System.out.println(d.toString());
            Thread.sleep(1000);
        }*/
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean stop = true;
                while (stop) {
                    try {
                        System.out.println("==="+Thread.currentThread().getName()+"===");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };*/

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("hello");
                TimeUnit.SECONDS.sleep(2);
                return "hello";
            }
        };
        System.out.println(callable.call());
    }

    public static Dd get(Map m, String k) {
        Set<Dd> set = m.keySet();
        for (Dd dd : set) {
            if(k.equals(dd.a))
                return dd;
        }
        return null;
    }
}
