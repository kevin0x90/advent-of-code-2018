import math
import collections
import re

players = 430 
lastMarble = 71588 * 100

circle = collections.deque()
circle.append(0)

maxScore = 0
playerToScoreMap = collections.defaultdict(int)
player = 1

for marble in range(1, lastMarble + 1):
  if (marble % 23) == 0:
    circle.rotate(-7)
    playerToScoreMap[player] += (marble + circle.pop())

    if playerToScoreMap[player] > maxScore:
      maxScore = playerToScoreMap[player]
  else:
    circle.rotate(2)
    circle.append(marble)

  player += 1

  if player > players:
    player = 1

print(maxScore)