package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.chapter314.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable task = tasks.poll(); task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Runnable r1 = runNewTask();
        Runnable r2 = runNewTask();
        Runnable r3 = runNewTask();
        Runnable r4 = runNewTask();
        Runnable r5 = runNewTask();
        threadPool.work(r1);
        threadPool.work(r2);
        threadPool.work(r3);
        threadPool.work(r4);
        threadPool.work(r5);
        threadPool.shutdown();
    }

    private static Runnable runNewTask() {
        return () -> System.out.printf("Задача %s работает \n", Thread.currentThread().getName());
    }
}
