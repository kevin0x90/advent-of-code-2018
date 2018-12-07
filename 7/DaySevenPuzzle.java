import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Set;

class Solver {
    public static void main(final String[] args) throws FileNotFoundException {
        final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\7\\DaySevenPuzzle.txt");
        final Map<String, Set<String>> adjacencyList = new HashMap<>();
        final Map<String, Set<String>> parentList = new HashMap<>();

        try (final Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                scanner.next();
                final String before = scanner.next();

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

                adjacencyList.putIfAbsent(after, new TreeSet<>());
            }
        }

        while (adjacencyList.size() > 0) {

            String rootNode = "";
            for (String parent : adjacencyList.keySet()) {
                if (!parentList.containsKey(parent) || parentList.get(parent).size() == 0) {
                    rootNode = parent;
                    break;
                }
            }

            for (final String child : adjacencyList.get(rootNode)) {
                parentList.get(child).remove(rootNode);
            }

            adjacencyList.remove(rootNode);
            System.out.print(rootNode);
        }
    }
}