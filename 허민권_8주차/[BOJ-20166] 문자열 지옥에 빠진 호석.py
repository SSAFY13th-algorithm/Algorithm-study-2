from collections import defaultdict
import sys

input = sys.stdin.readline
N, M, K = map(int, input().split())

grid = [list(input().strip()) for _ in range(N)]
max_length = 5
precomp = defaultdict(int)

directions = [
    (0, 1),
    (-1, 1),
    (-1, 0),
    (-1, -1),
    (0, -1),
    (1, -1),
    (1, 0),
    (1, 1),
]


def dfs(y, x, cur_string):
    precomp[cur_string] += 1

    if len(cur_string) == max_length:
        return

    for dy, dx in directions:
        next_y, next_x = y + dy, x + dx
        if next_y == N:
            next_y = 0
        if next_y == -1:
            next_y = N - 1
        if next_x == M:
            next_x = 0
        if next_x == -1:
            next_x = M - 1

        dfs(next_y, next_x, cur_string + grid[next_y][next_x])


for y in range(N):
    for x in range(M):
        dfs(y, x, grid[y][x])

for _ in range(K):
    query = input().rstrip()
    print(precomp[query])
