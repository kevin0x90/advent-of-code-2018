import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Math.*;

class Point {
    private final int x;
    private final int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistanceTo(final Point otherPoint) {
        return abs(x - otherPoint.x) + abs(y - otherPoint.y);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Point)) {
            return false;
        }

        final Point otherPoint = (Point) other;

        return x == otherPoint.x && y == otherPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}

class Solver {
    public static void main(final String[] args) throws FileNotFoundException {
        final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\6\\DaySixPuzzle.txt");
        final Map<Point, Set<Point>> givenPoints = new HashMap<>();

        int maxX = 0;
        int maxY = 0;

        try (final Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                final String[] lineParts = scanner.nextLine().split(",");
                final int x = Integer.valueOf(lineParts[0].trim());
                final int y = Integer.valueOf(lineParts[1].trim());

                maxX = max(x, maxX);
                maxY = max(y, maxY);

                givenPoints.putIfAbsent(new Point(x, y), new HashSet());
            }
        }

        for (int x = 0; x <= maxX; ++x) {
            for (int y = 0; y <= maxY; ++y) {
                final Point currentPoint = new Point(x, y);
                final Map<Integer, Set<Point>> distanceToPoints = new HashMap<>();
                for (final Point givenPoint : givenPoints.keySet()) {
                    final int distance = givenPoint.getDistanceTo(currentPoint);
                    if (distanceToPoints.containsKey(distance)) {
                        distanceToPoints.get(distance).add(givenPoint);
                    } else {
                        final Set<Point> newSet = new HashSet<>();
                        newSet.add(givenPoint);
                        distanceToPoints.put(distance, newSet);
                    }
                }

                int minDistance = distanceToPoints.keySet().stream().min(Integer::compare).get();

                if (distanceToPoints.get(minDistance).size() == 1) {
                    for (final Point point : distanceToPoints.get(minDistance)) {
                        givenPoints.get(point).add(currentPoint);
                    }
                }

            }
        }

        final Set<Point> pointsToRemove = new HashSet<>();

        for (final Point point : givenPoints.keySet()) {

            for (final Point subPoint : givenPoints.get(point)) {
                if (subPoint.getX() == 0 && subPoint.getY() <= maxY) {
                    pointsToRemove.add(point);
                    break;
                }

                if (subPoint.getY() == 0 && subPoint.getX() <= maxX) {
                    pointsToRemove.add(point);
                    break;
                }

                if (subPoint.getX() == maxX && subPoint.getY() <= maxY) {
                    pointsToRemove.add(point);
                    break;
                }

                if (subPoint.getY() == maxY && subPoint.getX() <= maxX) {
                    pointsToRemove.add(point);
                    break;
                }
            }
        }

        for (final Point p : pointsToRemove) {
            givenPoints.remove(p);
        }

        int max = 0;
        for (final Point p : givenPoints.keySet()) {
            if (givenPoints.get(p).size() > max) {
                max = givenPoints.get(p).size();
            }
        }

        System.out.println(max);
    }
}