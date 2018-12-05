import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuilder;

class Solver {

  private static int reactSequence(final String sequence) {
    final Set<Integer> reactedIndizes = new HashSet<>();
    int sequenceLength = sequence.length();
    int prevNumberOfReactions = 0;

    do {
      prevNumberOfReactions = reactedIndizes.size();
      for (int i = 0, k = 1; i < sequenceLength - 1; ++i, ++k) {

        while (reactedIndizes.contains(i) && i + 1 < sequenceLength - 1) {
          ++i;
          if (i == k && i + 1 < sequenceLength - 1) {
            ++i;
          }
        }

        while (reactedIndizes.contains(k) && k + 1 < sequenceLength - 1) {
          ++k;
          if (k == i && k + 1 < sequenceLength - 1) {
            ++k;
          }
        }

        final char a = sequence.charAt(i);
        final char b = sequence.charAt(k);

        if (Character.toLowerCase(a) == Character.toLowerCase(b) && 
          (Character.isLowerCase(a) && Character.isUpperCase(b) || 
          Character.isLowerCase(b) && Character.isUpperCase(a))) {
            reactedIndizes.add(i);
            reactedIndizes.add(k);
        }
      }
    } while (reactedIndizes.size() > prevNumberOfReactions);

    return sequence.length() - reactedIndizes.size();
  }

  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\5\\DayFivePuzzle.txt");

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {

        final String sequence = scanner.next();
        System.out.println(reactSequence(sequence));

        final Set<Character> sequenceCharacters = new HashSet<>();
        for (int i = 0; i < sequence.length(); ++i) {
          sequenceCharacters.add(Character.toLowerCase(sequence.charAt(i)));
        }

        int shortestLength = Integer.MAX_VALUE;
        for (final char character : sequenceCharacters) {
          final StringBuilder newSequence = new StringBuilder();
          for (int k = 0; k < sequence.length(); ++k) {
            if (character == Character.toLowerCase(sequence.charAt(k))) {
              continue;
            }

            newSequence.append(sequence.charAt(k));
          }

          int length = reactSequence(newSequence.toString());
          if (length < shortestLength) {
            shortestLength = length;
          }
        }

        System.out.println(shortestLength);
      }
    }
  }
}