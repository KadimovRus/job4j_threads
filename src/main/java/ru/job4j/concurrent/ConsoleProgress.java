package ru.job4j.concurrent;

import java.util.Arrays;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
        progress.join();
    }

    @Override
    public void run() {
        Character[] process = {'-', '\\', '|', '/'};

        while (!Thread.currentThread().isInterrupted()) {
            Arrays.stream(process).forEach(el -> {
                try {
                    Thread.sleep(500);
                    System.out.print("\r load: " + el);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
