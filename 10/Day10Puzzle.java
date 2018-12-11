import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solver {
    public static void main(final String[] args) throws FileNotFoundException {
        final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\10\\Day10Puzzle.txt");
        final Pattern inputPattern = Pattern.compile("position=<(?<xy>.*)>\\svelocity=<(?<vel>.*)>");
        final List<Point> points = new ArrayList<>();
        final List<Point> velocities = new ArrayList<>();
        int maxX = 0;
        int minX = 0;

        int maxY = 0;
        int minY = 0;

        try (final Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                final String currentLine = scanner.nextLine();
                final Matcher matcher = inputPattern.matcher(currentLine);
                if (matcher.matches()) {
                    final String[] xyParts = matcher.group("xy").trim().split(",");
                    final int x = Integer.valueOf(xyParts[0].trim());
                    maxX = Math.max(x, maxX);
                    minX = Math.min(x, minX);

                    final int y = Integer.valueOf(xyParts[1].trim());
                    maxY = Math.max(y, maxY);
                    minY = Math.min(y, minY);

                    final String[] velParts = matcher.group("vel").trim().split(",");
                    final int xVel = Integer.valueOf(velParts[0].trim());
                    final int yVel = Integer.valueOf(velParts[1].trim());

                    points.add(new Point(x, y));
                    velocities.add(new Point(xVel, yVel));
                }
            }
        }

        while (true) {

            if (maxY <= 116 && maxX <= 167) {
                for (int y = minY; y <= maxY; ++y) {
                    for (int x = minX; x <= maxX; ++x) {
                        if (points.contains(new Point(x, y))) {
                            System.out.print('#');
                        } else {
                            System.out.print('.');
                        }
                    }
                    System.out.println();
                }
                break;
            }

            maxX = 0;
            minX = 0;

            maxY = 0;
            minY = 0;

            for (int i = 0; i < points.size(); ++i) {
                final Point currentPoint = points.get(i);
                final Point currentVelocity = velocities.get(i);

                currentPoint.x += currentVelocity.x;
                currentPoint.y += currentVelocity.y;

                maxX = Math.max(maxX, currentPoint.x);
                minX = Math.min(minX, currentPoint.x);

                maxY = Math.max(maxY, currentPoint.y);
                minY = Math.min(minY, currentPoint.y);
            }
        }
    }
}