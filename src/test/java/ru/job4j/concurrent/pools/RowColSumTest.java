package ru.job4j.concurrent.pools;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class RowColSumTest {
    @Test
    public void whenSumThenTrue() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        StringBuilder exp = new StringBuilder()
                .append("[Sums{rowSum=6, colSum=12}, ")
                .append("Sums{rowSum=15, colSum=15}, ")
                .append("Sums{rowSum=24, colSum=18}]");
        assertEquals(exp.toString(), Arrays.toString(RowColSum.sum(matrix)));
    }

    @Test
    public void whenAsyncSumThenTrue() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        StringBuilder exp = new StringBuilder()
                .append("[Sums{rowSum=10, colSum=28}, ")
                .append("Sums{rowSum=26, colSum=32}, ")
                .append("Sums{rowSum=42, colSum=36}, ")
                .append("Sums{rowSum=58, colSum=40}]");
        assertEquals(exp.toString(), Arrays.toString(RowColSum.asyncSum(matrix)));
    }
}