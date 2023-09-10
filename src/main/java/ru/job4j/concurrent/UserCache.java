package ru.job4j.concurrent;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@NotThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public synchronized void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public synchronized User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public synchronized List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
