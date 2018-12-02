import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\2\\DayTwoPuzzleTwo.txt");
    final List<String> ids = new ArrayList<>();

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        ids.add(scanner.next());
      }
    }

    for (int i = 0; i < ids.size(); ++i) {
      final String id1 = ids.get(i);

      for (int k = i; k < ids.size(); ++k) {
        final String id2 = ids.get(k);
        int diffCount = 0;

        for (int c = 0; c < id1.length(); ++c) {
          if (id1.charAt(c) != id2.charAt(c)) {
            ++diffCount;
          }
        }

        if (diffCount == 1) {
          for (int c = 0; c < id1.length(); ++c) {
            if (id1.charAt(c) == id2.charAt(c)) {
              System.out.print(id1.charAt(c));
            }
          }

          return;   
        }
      }
    }
  }
}