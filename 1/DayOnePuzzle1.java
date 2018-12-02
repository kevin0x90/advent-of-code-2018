import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C://Users//k3v1n//Documents//adventOfCode//DayOnePuzzle1.txt");
    int result = 0;
    
    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        result += scanner.nextInt();
      }
    }

    System.out.println(result);
  }
}