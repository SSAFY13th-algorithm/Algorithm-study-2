R,C,T = map(int,input().split())

cur = [list(map(int,input().split())) for _ in range(R)]

def is_ok(y,x):
    return 0<=y<R and 0<=x<C

def is_clean(y,x):
    if y == up_idx and x == 0:
        return True
    if y == down_idx and x== 0:
        return True
    return False


up_idx = -1
down_idx = -1
for i in range(R):
    if cur[i][0] == -1:
        up_idx = i
        down_idx = i+1
        break
cur[up_idx][0] = 0
cur[down_idx][0] = 0

for t in range(T):
    next = [[0]*C for _ in range(R)]

    for y in range(R):
        for x in range(C):
            if cur[y][x] != 0:
                count = 0
                for (dy,dx) in [(0,1),(-1,0),(0,-1),(1,0)]:
                    next_y = y + dy
                    next_x = x+ dx

                    if is_ok(next_y,next_x) and not is_clean(next_y,next_x):
                        count += 1
                        next[next_y][next_x] += cur[y][x] // 5
                next[y][x] += cur[y][x] - count * (cur[y][x]//5)
    # print(f'after {t+1}..')
    # print('------before--------')
    # for i in next:
    #     print(*i)
    # 윗놈부터 (아래, 왼쪽, 위, 오른쪽)
    for i in range(up_idx-1,0, -1):
        next[i][0] = next[i-1][0]
    for i in range(0,C-1):
        next[0][i] = next[0][i+1]
    for i in range(0, up_idx):
        next[i][-1] = next[i+1][-1]
    for i in range(C-1, 0, -1):
        next[up_idx][i] = next[up_idx][i-1]


    # 아랫놈 (위, 왼, 아래, 오른쪽)
    for i in range(down_idx+1, R-1):
        next[i][0] = next[i+1][0]
    for i in range(0, C-1):
        next[-1][i] = next[-1][i+1]
    for i in range(R-1, down_idx, -1):
        next[i][-1] = next[i-1][-1]
    for i in range(C-1,0, -1):
        next[down_idx][i] = next[down_idx][i-1]
    
    cur = next
    # print('------after--------')
    # for i in next:
    #     print(*i)
    # print('\n')

ans = 0
for i in range(R):
    ans += sum(cur[i])
print(ans)

