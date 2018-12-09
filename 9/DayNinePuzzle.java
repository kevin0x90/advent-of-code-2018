import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

class Solver {
    public static void main(final String[] args) throws FileNotFoundException {
        final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\9\\DayNinePuzzle.txt");
        final Map<Integer, Integer> playerScores = new HashMap<>();
        final List<Integer> marbleCircle = new LinkedList<>();
        final Map<Integer, Integer> marbleIndexMap = new HashMap<>();

        try (final Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                final int numberOfPlayers = scanner.nextInt();
                IntStream.rangeClosed(1, numberOfPlayers)
                        .forEach(player -> playerScores.putIfAbsent(player, 0));

                scanner.next();
                scanner.next();
                scanner.next();
                scanner.next();
                scanner.next();
                final int lastMarble = scanner.nextInt();
                scanner.nextLine();

                int currentMarble = 0;
                marbleCircle.add(currentMarble);
                marbleIndexMap.put(currentMarble, 0);

                for (int i = 1; i <= lastMarble; ++i) {
                    int currentPlayer = i % playerScores.size();
                    if (currentPlayer == 0) {
                        currentPlayer = playerScores.size();
                    }
                    if (marbleCircle.size() == 1) {
                        marbleCircle.add(i);
                        marbleIndexMap.put(i, 1);
                        currentMarble = i;
                        continue;
                    }

                    if (i % 23 == 0) {
                        playerScores.put(currentPlayer, playerScores.get(currentPlayer) + i);

                        int marbleToRemoveIndex = (marbleIndexMap.get(currentMarble) - 7) % marbleCircle.size();
                        if (marbleToRemoveIndex < 0) {
                            marbleToRemoveIndex += marbleCircle.size();
                        }

                        playerScores.put(currentPlayer, playerScores.get(currentPlayer) + marbleCircle.get(marbleToRemoveIndex));
                        currentMarble = marbleCircle.get(marbleToRemoveIndex + 1);
                        marbleIndexMap.put(currentMarble, marbleToRemoveIndex + 1);

                        marbleCircle.remove(marbleToRemoveIndex);

                        for (final int marble : marbleIndexMap.keySet()) {
                            final int index = marbleIndexMap.get(marble);
                            if (index > marbleToRemoveIndex) {
                                marbleIndexMap.put(marble, index - 1);
                            }
                        }

                        continue;
                    }

                    final int start = (marbleIndexMap.get(currentMarble) + 1) % marbleCircle.size();

                    marbleCircle.add(start + 1, i);
                    marbleIndexMap.put(i, start + 1);
                    currentMarble = i;
                }

                int max = 0;
                for (final int player : playerScores.keySet()) {
                    final int playerScore = playerScores.get(player);
                    max = Math.max(playerScore, max);
                }

                System.out.println(max);
            }
        }
    }
}