package edu.hw3.Task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BackwardIterator<T> implements Iterator<T> {

    private final Stack<T> stack;

    public BackwardIterator(Collection<T> collection) {
        this.stack = new Stack<>();
        fillStack(collection.iterator());
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.stack.pop();
    }

    private void fillStack(Iterator<T> collectionIterator) {
        while (collectionIterator.hasNext()) {
            stack.push(collectionIterator.next());
        }
    }
}
