package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        first.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s %s%n", first.getState(), first.getName());
        }
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s %s%n", second.getState(), second.getName());
        }
        System.out.printf("%s %s%n", first.getState(), first.getName());
        System.out.printf("%s %s%n", second.getState(), second.getName());
        System.out.println("Работа завершена");
    }
}
