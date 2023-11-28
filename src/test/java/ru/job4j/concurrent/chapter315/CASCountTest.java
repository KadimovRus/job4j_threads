package ru.job4j.concurrent.chapter315;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CASCountTest {

    @Test
    public void testIncrementAndGet() {
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(1, counter.get());

        counter.increment();
        assertEquals(2, counter.get());
    }

    @Test
    public void testIncrementAndGetMultiThread() throws InterruptedException {
        CASCount counter = new CASCount();
        int threadsNum = 10;
        int iterations = 100;

        ExecutorService service = Executors.newFixedThreadPool(threadsNum);
        for (int i = 0; i < threadsNum; i++) {
            service.submit(() -> {
                for (int j = 0; j < iterations; j++) {
                    counter.increment();
                }
            });
        }

        service.shutdown();
        service.awaitTermination(60, TimeUnit.SECONDS);

        assertEquals(threadsNum * iterations, counter.get());
    }
}