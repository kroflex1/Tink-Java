package edu.project2.Solvers;

import edu.project2.Coordinate;
import org.jetbrains.annotations.Nullable;

public final class BFS extends IfoSolver {
    @Override
    @Nullable Coordinate getAndRemoveCoordinateFromLinkedList() {
        if (linkedList.isEmpty()) {
            return null;
        }
        return linkedList.remove();
    }
}

