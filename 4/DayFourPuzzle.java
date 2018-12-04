import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;

class Guard {
  private static final Pattern idPattern = Pattern.compile("#(?<id>\\d+)");
  private final String guardInfo;
  private String id = "";

  public Guard(final String guardInfo) {
    this.guardInfo = guardInfo;
    
    final Matcher matcher = idPattern.matcher(guardInfo);
    if (matcher.find()) {
      id = matcher.group("id");
    }
  }

  public String getId() {
    return id;
  }

  public boolean hasId() {
    return id != "";
  }

  public boolean fallsAsleep() {
    return guardInfo.contains("falls asleep");
  }

  public boolean beginsShift() {
    return guardInfo.contains("begins shift");
  }

  public boolean wakesUp() {
    return guardInfo.contains("wakes up");
  }
}

class Solver {
  public static void main(final String[] args) throws FileNotFoundException {
    final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\4\\DayFourPuzzle.txt");
    int guardId = -1;
    int minute = -1;
    final SortedMap<LocalDateTime, Guard> sortedGuards = new TreeMap<>();
    final Map<String, Integer> guardMinutesAsleep = new HashMap<>();
    final Map<String, Map<Integer, Integer>> guardAsleepMinuteStats = new HashMap<>();

    try (final Scanner scanner = new Scanner(input)) {
      while (scanner.hasNext()) {
        sortedGuards.put(getLocalDateTimeFromInput(scanner.next() + " " + scanner.next()), new Guard(scanner.nextLine()));
      }
    }

    String currentGuardId = "";
    boolean currentlyAsleep = false;
    LocalDateTime prevTime = null;
    for (final LocalDateTime dateTime : sortedGuards.keySet()) {
      final Guard guard = sortedGuards.get(dateTime);
      if (guard.hasId()) {
        currentGuardId = guard.getId();
        prevTime = dateTime;
        guardMinutesAsleep.putIfAbsent(currentGuardId, 0);
        guardAsleepMinuteStats.putIfAbsent(currentGuardId, new HashMap<Integer, Integer>());
      } else {
        if (guard.fallsAsleep()) {
          currentlyAsleep = true;
        } else if (guard.wakesUp()) {
          final int guardTimeAsleep = guardMinutesAsleep.get(currentGuardId);
          guardMinutesAsleep.put(currentGuardId, guardTimeAsleep + dateTime.getMinute() - prevTime.getMinute());

          final Map<Integer, Integer> minuteStats = guardAsleepMinuteStats.get(currentGuardId);
          for (int i = prevTime.getMinute(); i < dateTime.getMinute(); ++i) {
            if (minuteStats.containsKey(i)) {
              minuteStats.put(i, minuteStats.get(i) + 1);
            }

            minuteStats.putIfAbsent(i, 1);
          }
          
          currentlyAsleep = false;
        }

        prevTime = dateTime;
      }
    }

    int maxTimeAsleep = 0;
    String maxId = "";
    for (final String id : guardMinutesAsleep.keySet()) {
      if (guardMinutesAsleep.get(id) > maxTimeAsleep) {
        maxTimeAsleep = guardMinutesAsleep.get(id);
        maxId = id;
      }
    }

    int mostAsleepCount = 0;
    int mostAsleepMinute = -1;
    for (final int min : guardAsleepMinuteStats.get(maxId).keySet()) {
      if (guardAsleepMinuteStats.get(maxId).get(min) > mostAsleepCount) {
        mostAsleepCount = guardAsleepMinuteStats.get(maxId).get(min);
        mostAsleepMinute = min;
      }
    }

    System.out.println(Integer.valueOf(maxId) * mostAsleepMinute);

    int maxMinSleepCount = 0;
    int maxSleepMinute = 0;
    String maxSleepId = "";
    for (final String id : guardAsleepMinuteStats.keySet()) {
      for (final int min : guardAsleepMinuteStats.get(id).keySet()) {
        if (guardAsleepMinuteStats.get(id).get(min) > maxMinSleepCount) {
          maxMinSleepCount = guardAsleepMinuteStats.get(id).get(min);
          maxSleepMinute = min;
          maxSleepId = id;
        }
      }
    }

    System.out.println(Integer.valueOf(maxSleepId) * maxSleepMinute);
  }

  private static LocalDateTime getLocalDateTimeFromInput(final String input) {
    final String inputWithoutBrackets = input.substring(1, input.length() - 1);
    return LocalDateTime.parse(inputWithoutBrackets, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }
}