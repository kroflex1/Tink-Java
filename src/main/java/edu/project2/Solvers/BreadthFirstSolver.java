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
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSolver implements Solver {
    Set<Coordinate> visited;
    Map<Coordinate, Coordinate> track;
    Queue<Coordinate> queue;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) throws IllegalArgumentException {
        checkPoints(maze, start, end);
        visited = new HashSet<>();
        track = new HashMap<>();
        queue = new LinkedList<>();

        queue.add(start);
        track.put(start, null);
        while (track.containsKey(end)) {
            Coordinate startPoint = queue.poll();
            if (startPoint == null) {
                throw new IllegalArgumentException("Impossible to find the path  from a given start point");
            }
            for (Coordinate neighboringPoint : maze.getNeighboringPoints(startPoint)) {
                if (!visited.contains(neighboringPoint)) {
                    visited.add(neighboringPoint);
                    track.put(neighboringPoint, startPoint);
                    queue.add(neighboringPoint);
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
        if (!maze.isPointInBoundary(start) || !maze.isPointInBoundary(end)) {
            throw new IllegalArgumentException("Points are outside the boundary of the maze");
        }
        if (maze.getPointType(start) == CellType.WALL || maze.getPointType(end) == CellType.WALL) {
            throw new IllegalArgumentException("Impossible to find the path, since one of the points is a wall");
        }
    }
}

