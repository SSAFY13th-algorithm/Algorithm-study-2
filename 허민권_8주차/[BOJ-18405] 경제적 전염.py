import sys
from collections import defaultdict

input = sys.stdin.readline

N, K = map(int, input().split())
grid = [list(map(int, input().split())) for _ in range(N)]
visited = [[False] * N for _ in range(N)]
S, X, Y = map(int, input().split())

virus = defaultdict(list)

for y in range(N):
    for x in range(N):
        if grid[y][x] == 0:
            continue
        virus[grid[y][x]].append((y, x))

nums = sorted(virus.keys())

for _ in range(S):
    for i in nums:
        next_pos = []
        for y, x in virus[i]:
            for dy, dx in [(0, 1), (-1, 0), (0, -1), (1, 0)]:
                next_y, next_x = y + dy, x + dx
                if next_y < 0 or next_y >= N or next_x < 0 or next_x >= N:
                    continue

                if grid[next_y][next_x] == 0:
                    grid[next_y][next_x] = i
                    next_pos.append((next_y, next_x))
        virus[i] = next_pos


print(grid[X - 1][Y - 1])
