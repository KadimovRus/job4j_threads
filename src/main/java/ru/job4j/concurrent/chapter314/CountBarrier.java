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
                    count++;
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
