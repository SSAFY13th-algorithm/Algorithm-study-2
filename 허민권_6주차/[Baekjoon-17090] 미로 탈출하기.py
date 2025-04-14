N,M = map(int,input().split())

maze = [list(input()) for _ in range(N)]
check = [[False] * (M) for _ in range(N)]
dy = [0,-1,0,1]
dx = [1,0,-1,0]

def dfs(i,j):

    s = []
    s.append((i,j))

    while s:
        i,j = s.pop()
        check[i][j] = True

        for k in range(4):
            next_y = i + dy[k]
            next_x = j + dx[k]
            if 0<=next_y<N and 0<=next_x<M and not check[next_y][next_x]:
                if k == 0:
                    if maze[next_y][next_x] == 'L':
                        s.append((next_y,next_x))
                if k == 1:
                    if maze[next_y][next_x] == 'D':
                        s.append((next_y,next_x))
                if k == 2:
                    if maze[next_y][next_x] == 'R':
                        s.append((next_y,next_x))
                if k == 3:
                    if maze[next_y][next_x] == 'U':
                        s.append((next_y,next_x))

            


for i in range(N):
    for j in range(M):
        if check[i][j]: continue

        if i == 0 and maze[i][j] == 'U':
            dfs(i, j)
        if i == N-1 and maze[i][j] == 'D':
            dfs(i, j)
        if j == 0 and maze[i][j] == 'L':
            dfs(i, j)
        if j == M-1 and maze[i][j] == 'R':
            dfs(i, j)

ans = 0
for i in range(N):
    ans += check[i].count(1)
print(ans)
