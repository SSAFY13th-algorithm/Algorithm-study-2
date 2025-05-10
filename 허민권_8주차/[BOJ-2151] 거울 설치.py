from collections import deque
import math

N = int(input())

house = [list(input()) for _ in range(N)]

doors = []
mirrors = []
visited = [[[math.inf] * 4 for _ in range(N)] for _ in range(N)]

for y in range(N):
    for x in range(N):
        if house[y][x] == "#":
            doors.append((y, x))


def process(next_y, next_x, next_dir, next_count):
    global visited

    if visited[next_y][next_x][next_dir] > next_count:
        visited[next_y][next_x][next_dir] = next_count
        dq.append((next_y, next_x, next_dir, next_count))


RIGHT = 0
UP = 1
LEFT = 2
DOWN = 3

dq = deque()
sy, sx = doors[0]


d = [(0, 1), (-1, 0), (0, -1), (1, 0)]

dq.append((sy, sx, UP, 0))
dq.append((sy, sx, LEFT, 0))
dq.append((sy, sx, RIGHT, 0))
dq.append((sy, sx, DOWN, 0))
visited[sy][sx] = [0, 0, 0, 0]

ans = math.inf
while dq:
    cur_y, cur_x, cur_dir, cur_count = dq.popleft()
    if (cur_y, cur_x) == doors[1]:
        ans = min(ans, cur_count)
        continue

    dy, dx = d[cur_dir]
    next_y, next_x = cur_y + dy, cur_x + dx
    if next_y < 0 or next_y >= N or next_x < 0 or next_x >= N:
        continue
    if house[next_y][next_x] == "*":
        continue

    process(next_y, next_x, cur_dir, cur_count)

    if house[next_y][next_x] == "!":
        if cur_dir == UP or cur_dir == DOWN:
            process(next_y, next_x, LEFT, cur_count + 1)
            process(next_y, next_x, RIGHT, cur_count + 1)
        if cur_dir == LEFT or cur_dir == RIGHT:
            process(next_y, next_x, UP, cur_count + 1)
            process(next_y, next_x, DOWN, cur_count + 1)

print(ans)
