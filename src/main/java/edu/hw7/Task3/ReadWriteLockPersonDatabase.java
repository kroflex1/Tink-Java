package edu.hw7.Task3;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase extends InMemoryPersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    @Override
    public void add(Person person) {
        writeLock.lock();
        super.add(person);
        writeLock.unlock();
    }

    @Override
    public synchronized void delete(int id) {
        writeLock.lock();
        super.delete(id);
        writeLock.unlock();
    }

    @Override
    public List<Person> findByName(String name) {
        readLock.lock();
        try {
            return super.findByName(name);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        readLock.lock();
        try {
            return super.findByAddress(address);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        readLock.lock();
        try {
            return super.findByPhone(phone);
        } finally {
            readLock.unlock();
        }
    }
}
