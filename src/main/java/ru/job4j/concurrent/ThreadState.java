package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s %s%n", first.getState(), first.getName());
            System.out.printf("%s %s%n", second.getState(), second.getName());
        }

        System.out.printf("%s %s%n", first.getState(), first.getName());
        System.out.printf("%s %s%n", second.getState(), second.getName());
        System.out.println("Работа завершена");
    }
}
