package ru.job4j.concurrent.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = calculateSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = getTask(matrix, i).get();
        }
        return sums;
    }

    private static Sums calculateSums(int[][] matrix, int i) {
        Sums sums = new Sums();
        int sumRow = 0;
        for (int j = 0; j < matrix.length; j++) {
            sumRow = sumRow + matrix[i][j];
        }
        sums.setRowSum(sumRow);

        int sumCol = 0;
        for (int j = 0; j < matrix.length; j++) {
            sumCol = sumCol + matrix[j][i];
        }
        sums.setColSum(sumCol);
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> calculateSums(matrix, i));
    }
}
