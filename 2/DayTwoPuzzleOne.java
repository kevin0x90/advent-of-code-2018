import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

class Solver {
  public static void main(final String args[]) throws FileNotFoundException {
    
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\2\\DayTwoPuzzleOne.txt");
    int twice = 0;
    int threeTimes = 0;

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        final String boxId = scanner.next();
        final Map<Character, Integer> stats = getLetterStats(boxId);

        boolean foundTwice = false;
        boolean foundThreeTimes = false;
        for (final char letter : stats.keySet()) {
          if (!foundTwice && stats.get(letter) == 2) {
            ++twice;
            foundTwice = true;
          }

          if (!foundThreeTimes && stats.get(letter) == 3) {
            ++threeTimes;
            foundThreeTimes = true;
          }

          if (foundTwice && foundThreeTimes) {
            break;
          }
        }
      }
    }

    System.out.println(twice * threeTimes);
  }

  private static Map<Character, Integer> getLetterStats(final String string) {
    final Map<Character, Integer> stats = new HashMap<>();

    for (final char letter : string.toCharArray()) {
      if (stats.containsKey(letter)) {
        stats.put(letter, stats.get(letter) + 1);
      }

      stats.putIfAbsent(letter, 1);
    }

    return stats;
  }
}