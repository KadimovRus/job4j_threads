package ru.job4j.concurrent.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public final class ParseFile<T> implements GetContent<T> {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    @Override
    public synchronized String content(Predicate<T> filter) {
        try (InputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            Integer data;
            while ((data = i.read()) != -1) {
                if (filter.test((T) data)) {
                    output.append(data);
                }
            }
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

