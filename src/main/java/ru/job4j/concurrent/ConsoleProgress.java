package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    char[] process = new char[]{'-', '\\', '|', '/'};
    int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                i = i++ > 2 ? 0 : i;
                System.out.print("\r load: " + process[i]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
