package ru.job4j.concurrent.cash;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountStorage {
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        accounts.put(account.id(), account);
        return true;
    }

    public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public void delete(int id) {
        Optional<Account> accountOptional = getById(id);
        accountOptional.ifPresent(accounts::remove);
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> optionalAccountFrom = getById(fromId);
        Optional<Account> optionalAccountTo = getById(toId);
        if (optionalAccountFrom.isPresent() && optionalAccountTo.isPresent()) {
            Account accountFrom = optionalAccountFrom.get();
            Account accountTo = optionalAccountTo.get();
            if (accountFrom.amount() >= amount) {
                Account newAccountTo = new Account(accountTo.id(), accountTo.amount() + amount);
                Account newAccountFrom = new Account(accountFrom.id(), accountFrom.amount() - amount);
                update(newAccountTo);
                update(newAccountFrom);
                return true;
            }
        }
        return false;
    }
}
