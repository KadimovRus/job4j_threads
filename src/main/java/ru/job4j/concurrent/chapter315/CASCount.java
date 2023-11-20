package ru.job4j.concurrent.chapter315;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        int ref;

        do {
           ref = count.intValue();
           temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        return count.get();
    }
}
