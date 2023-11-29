package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.chapter314.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);
    int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread();
            thread.start();
            threads.add(thread);
            tasks.poll();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
