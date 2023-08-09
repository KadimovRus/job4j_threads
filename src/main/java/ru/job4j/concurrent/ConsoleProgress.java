package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    char[] process = new char[]{'-', '\\', '|', '/'};
    int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
        progress.join();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                i = i++ < process.length-1 ? i : 0;
                System.out.print("\r load: " + process[i]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
