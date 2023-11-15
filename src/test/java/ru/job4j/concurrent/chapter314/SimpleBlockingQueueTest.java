package ru.job4j.concurrent.chapter314;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {

    @Test
    public void test() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Integer value = null;
                try {
                    value = queue.poll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Assertions.assertEquals(Integer.valueOf(i), value);

            }
        });
    }

}