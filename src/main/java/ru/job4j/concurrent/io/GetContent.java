package ru.job4j.concurrent.io;

import java.io.FileNotFoundException;
import java.util.function.Predicate;

public interface GetContent<T> {
    String content(Predicate<T> filter) throws FileNotFoundException;
}
