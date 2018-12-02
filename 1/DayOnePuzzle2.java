import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C://Users//k3v1n//Documents//adventOfCode//DayOnePuzzle2.txt");
    final Set<Integer> seenFrequencies = new HashSet<>();
    int result = 0;
    
    while (true) {
      try (Scanner scanner = new Scanner(input)) {
        while (scanner.hasNext()) {
          final int currentNumber = scanner.nextInt();
          result += currentNumber;

          if (seenFrequencies.contains(result)) {
            System.out.println(result);
            return;
          }

          seenFrequencies.add(result);
        }
      }
    }
   }
}