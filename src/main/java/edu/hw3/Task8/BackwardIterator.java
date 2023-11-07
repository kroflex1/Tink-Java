package edu.hw3.Task8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {

    private final List<T> elements;
    private int currentIndex;

    public BackwardIterator(Collection<T> collection) {
        elements = new ArrayList<>();
        fillList(collection.iterator());
        currentIndex = this.elements.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.elements.get(currentIndex--);
    }

    private void fillList(Iterator<T> collectionIterator) {
        while (collectionIterator.hasNext()) {
            elements.add(collectionIterator.next());
        }
    }
}
