package ru.job4j.concurrent.chapter315;

import org.junit.Test;

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

}