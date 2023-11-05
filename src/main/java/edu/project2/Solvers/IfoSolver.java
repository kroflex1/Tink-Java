package edu.project2.Solvers;

import edu.project2.CellType;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

abstract sealed class IfoSolver implements Solver permits BFS, DFS {

    protected LinkedList<Coordinate> linkedList;
    private Set<Coordinate> visited;
    private Map<Coordinate, Coordinate> track;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        checkPoints(maze, start, end);
        visited = new HashSet<>();
        track = new HashMap<>();
        linkedList = new LinkedList<>();

        linkedList.add(start);
        track.put(start, null);
        visited.add(start);
        while (!track.containsKey(end)) {
            Coordinate startPoint = getAndRemoveCoordinateFromLinkedList();
            if (startPoint == null) {
                throw new RuntimeException("Impossible to find the path  from a given start point");
            }
            for (Coordinate neighboringPoint : maze.getNeighboringPoints(startPoint)) {
                if (maze.getPointType(neighboringPoint) != CellType.WALL && !visited.contains(neighboringPoint)) {
                    visited.add(neighboringPoint);
                    track.put(neighboringPoint, startPoint);
                    linkedList.add(neighboringPoint);
                }
            }
        }
        return getPath(end, track);
    }

    private List<Coordinate> getPath(Coordinate end, Map<Coordinate, Coordinate> track) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate currentPoint = end;
        while (currentPoint != null) {
            path.add(currentPoint);
            currentPoint = track.get(currentPoint);
        }
        return path.reversed();
    }

    private void checkPoints(Maze maze, Coordinate start, Coordinate end) {
        if (maze.getPointType(start) == CellType.WALL || maze.getPointType(end) == CellType.WALL) {
            throw new IllegalArgumentException("Impossible to find the path, since one of the points is a wall");
        }
    }

    @Nullable
    abstract Coordinate getAndRemoveCoordinateFromLinkedList();
}
