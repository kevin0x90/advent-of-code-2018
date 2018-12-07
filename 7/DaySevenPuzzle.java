import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Set;
import java.util.ArrayList;

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\7\\DaySevenPuzzle.txt");
    final Map<String, Set<String>> adjacencyList = new HashMap<>();
    final Map<String, Set<String>> parentList = new HashMap<>();
    String rootNode = "";

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        scanner.next();
        final String before = scanner.next();

        if (rootNode == "") {
          rootNode = before;
        }

        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        final String after = scanner.next();
        scanner.nextLine();

        if (parentList.containsKey(after)) {
          parentList.get(after).add(before);
        } else {
          final Set<String> parentNodes = new TreeSet<>();
          parentNodes.add(before);
          parentList.put(after, parentNodes);
        }

        if (adjacencyList.containsKey(before)) {
          adjacencyList.get(before).add(after);
        } else {
          final Set<String> nodes = new TreeSet<>();
          nodes.add(after);
          adjacencyList.put(before, nodes);
        }
      }
    }

    System.out.println(parentList);
    System.out.println(adjacencyList);
    
    walkTree(rootNode, adjacencyList, parentList);
    
  }

  private static void walkTree(String rootNode, Map<String, Set<String>> adjacencyList, Map<String, Set<String>> parentList) {
    System.out.print(rootNode);

    if (adjacencyList.get(rootNode) == null) {
      return;
    }

    for (final String child : adjacencyList.get(rootNode)) {
      parentList.get(child).remove(rootNode);
      if (parentList.get(child).size() == 0) {
        walkTree(child, adjacencyList, parentList);
      }
    }
  }
}