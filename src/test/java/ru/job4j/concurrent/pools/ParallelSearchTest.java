package ru.job4j.concurrent.pools;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

public class ParallelSearchTest {

    @Test
    public void findElementInSmallArrayIsSuccess() {
        String[] strings = {"abc", "cda", "wer", "bat", "man"};
        String element = "bat";
        ForkJoinPool pool = new ForkJoinPool();
        Integer result = ParallelSearch.find(strings, element);
        Assert.assertEquals(3, result.intValue());
    }

    @Test
    public void findElementInLargeArrayIsSuccess() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 410, 31, 32, 23, 45, 35, 65, 76, 81, 95, 104, 14, 24, 34, 44, 52, 64, 71, 81, 66, 160};
        Integer element = 35;
        ForkJoinPool pool = new ForkJoinPool();
        Integer result = ParallelSearch.find(integers, element);
        Assert.assertEquals(14, result.intValue());
    }

    @Test
    public void findElementInLargeArrayIsFailed() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 410, 31, 32, 23, 45, 35, 65, 76, 81, 95, 104, 14, 24, 34, 44, 52, 64, 71, 81, 66, 160};
        Integer element = 305;
        ForkJoinPool pool = new ForkJoinPool();
        Integer result = ParallelSearch.find(integers, element);
        Assert.assertEquals(-1, result.intValue());
    }

}