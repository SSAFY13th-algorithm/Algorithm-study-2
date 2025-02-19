from collections import deque

N,M = map(int,input().split())

board = [list(map(int,input().split())) for _ in range(N)]
check_board = [[False]*(M) for _ in range(N)]


H,W,sr,sc,fr,fc= map(int,input().split())
sr-=1
sc-=1
fr-=1
fc-=1


def is_ok(y,x):
    return 0<=y<N and 0<=x<M


ans = -1
dq = deque()

dq.append((sr,sc,0))

while dq:
    y,x,cur_count = dq.popleft()

    if y==fr and x==fc:
        ans = cur_count
        break

    for (dy,dx, dir) in [(-1,0, 'UP'),
                         (1,0, 'DOWN'),
                         (0,-1, 'LEFT'),
                         (0,1, 'RIGHT')]:

        next_y = y+dy
        next_x = x+dx
        count = 0
        if dir=='UP':
            for i in range(W):
                line_y = y+dy
                line_x = x+dx+i
                if is_ok(line_y,line_x) and board[line_y][line_x] == 0:
                    count+=1
            if count == W and not check_board[next_y][next_x]:
                check_board[next_y][next_x] = True
                dq.append((next_y,next_x,cur_count+1))

        if dir=='DOWN':
            for i in range(W):
                line_y = y+dy+H-1
                line_x = x+dx+i
                if is_ok(line_y,line_x) and board[line_y][line_x] == 0:
                    count+=1
            if count == W and not check_board[next_y][next_x]:
                check_board[next_y][next_x] = True
                dq.append((next_y,next_x,cur_count+1))
        if dir=='LEFT':
            for i in range(H):
                line_y = y+dy+i
                line_x = x+dx
                if is_ok(line_y,line_x) and board[line_y][line_x] == 0:
                    count+=1
            if count == H and not check_board[next_y][next_x]:
                check_board[next_y][next_x] = True
                dq.append((next_y,next_x,cur_count+1))
        if dir=='RIGHT':
            for i in range(H):
                line_y = y+dy+i
                line_x = x+dx+W-1
                if is_ok(line_y,line_x) and board[line_y][line_x] == 0:
                    count+=1
            if count == H and not check_board[next_y][next_x]:
                check_board[next_y][next_x] = True
                dq.append((next_y,next_x,cur_count+1))
        



print(ans)
