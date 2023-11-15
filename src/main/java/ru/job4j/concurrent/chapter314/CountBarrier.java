package ru.job4j.concurrent.chapter314;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() throws InterruptedException {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }

    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread thread1 = getThread(countBarrier);
        Thread thread2 = getThread(countBarrier);
        Thread thread3 = getThread(countBarrier);
        Thread thread4 = getThread(countBarrier);
        Thread thread5 = getThread(countBarrier);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            countBarrier.count();
        }

        System.out.println("Threads finished");
    }

    private static Thread getThread(CountBarrier countBarrier) {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started ");
            try {
                countBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
