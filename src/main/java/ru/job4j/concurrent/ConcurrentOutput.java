package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread threadOne = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread threadTwo = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        threadOne.start();
        threadTwo.start();
        System.out.println(Thread.currentThread().getName());
    }
}
