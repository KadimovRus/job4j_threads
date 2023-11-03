package ru.job4j.concurrent;

import java.io.FileNotFoundException;
import java.util.function.Predicate;

public interface GetContent {
    String content(Predicate<Character> filter) throws FileNotFoundException;
}
