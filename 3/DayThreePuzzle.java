import java.io.FileNotFoundException;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

class Claim {
  private final String id;
  private boolean overlaps;

  public Claim(final String id, final boolean overlaps) {
    this.id = id;
    this.overlaps = overlaps;
  }

  public String getId() {
    return id;
  }

  public boolean getOverlaps() {
    return overlaps;
  }

  public void setOverlaps(final boolean overlaps) {
    this.overlaps = overlaps;
  }
}

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

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }

    if (!(other instanceof Point)) {
      return false;
    }

    final Point otherPoint = (Point)other;

    return x == otherPoint.x && y == otherPoint.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "[" + x + ", " + y + "]";
  }
}

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\3\\DayThreePuzzle.txt");
    final Map<Point, Claim> points = new HashMap<>();
    final Set<String> overlappingClaimIds = new HashSet<>();
    final Set<String> claimIds = new HashSet<>();

    int overlapCount = 0;

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        final String claimId = scanner.next();
        claimIds.add(claimId);
        scanner.next();
        final String coordinateClaim = scanner.next().replace(':', ' ').trim();
        final String[] coordinateParts = coordinateClaim.split(",");
        final int x = Integer.valueOf(coordinateParts[0]);
        final int y = Integer.valueOf(coordinateParts[1]);

        final String dimensionClaim = scanner.next().trim();
        final String[] dimensionParts = dimensionClaim.split("x");
        final int width = Integer.valueOf(dimensionParts[0]);
        final int height = Integer.valueOf(dimensionParts[1]);

        for (int x1 = 0; x1 < width; ++x1) {
          for (int y1 = 0; y1 < height; ++y1) {
            final Point point = new Point(x + x1, y + y1);

            if (!points.containsKey(point)) {
              points.put(point, new Claim(claimId, false));
            } else {
              final Claim oldClaim = points.get(point);
              overlappingClaimIds.add(claimId);
              overlappingClaimIds.add(oldClaim.getId());
              
              if (!oldClaim.getOverlaps()) {
                ++overlapCount;
                oldClaim.setOverlaps(true);
              }
            }
          }
        }
      }
    }

    System.out.println(overlapCount);

    for (final String claimId : claimIds) {
      if (!overlappingClaimIds.contains(claimId)) {
        System.out.println(claimId);
        return;
      }
    }
  }
}