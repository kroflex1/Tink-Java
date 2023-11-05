package edu.project2.Solvers;

import edu.project2.CellType;
import edu.project2.Coordinate;
import edu.project2.Maze;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class BFS extends IfoSolver {
    @Override
    @Nullable Coordinate getAndRemoveCoordinateFromLinkedList() {
        if(linkedList.isEmpty())
            return null;
        return linkedList.remove();
    }
}

