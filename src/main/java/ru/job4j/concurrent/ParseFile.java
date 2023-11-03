package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile implements GetContent {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    @Override
    public String content(Predicate<Character> filter) throws FileNotFoundException {
        try (InputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

