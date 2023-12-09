package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T target;

    public ParallelSearch(T[] array, int from, int i, T target) {
        this.array = array;
        this.from = from;
        this.to = i;
        this.target = target;
    }

    public static <T> Integer find(T[] array, T value) {
        ForkJoinPool pool = new ForkJoinPool();
        ParallelSearch task = new ParallelSearch(array, 0, array.length, value);
        Integer result = (Integer) pool.invoke(task);
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            Integer i = getIndexForTarget();
            if (i != null) {
                return i;
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch leftTask = new ParallelSearch(array, from, mid, target);
        ParallelSearch rightTask = new ParallelSearch(array, mid + 1, to, target);

        leftTask.fork();
        rightTask.fork();
        return Math.max((Integer) leftTask.join(), (Integer) rightTask.join());
    }

    private Integer getIndexForTarget() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 410, 31, 32, 23, 45, 35, 65, 76, 81, 95, 104, 14, 24, 34, 44, 52, 64, 71, 81, 66, 160};
        int target = 34;
        ForkJoinPool pool = new ForkJoinPool();
        ParallelSearch task = new ParallelSearch(array, 0, array.length, target);
        Integer result = (Integer) pool.invoke(task);
        System.out.println("Target element is at index " + result);
    }

}
