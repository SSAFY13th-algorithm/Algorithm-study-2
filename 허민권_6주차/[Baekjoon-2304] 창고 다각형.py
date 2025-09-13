N = int(input())

lines = sorted([tuple(map(int,input().split())) for _ in range(N)])
area = [[0] * (1001) for _ in range(1001)]

def paint(option=1):
    global area

    start_pos = 0 # 지붕위치
    start_height = 0 # 지붕높이

    for cur_pos, cur_height in lines:
        if start_height < cur_height:
            for y in range(1,cur_height+1):
                area[y][cur_pos] +=1
            for y in range(1,start_height+1):
                if option:
                    for x in range(start_pos+1,cur_pos):
                        area[y][x] += 1
                else:
                    for x in range(cur_pos+1, start_pos):
                        area[y][x] += 1
            start_height = cur_height
            start_pos = cur_pos
    if option:
        for y in range(1,start_height+1):
            for x in range(start_pos+1, 1001):
                area[y][x] += 1
    else:
        for y in range(1,start_height+1):
            for x in range(start_pos-1, -1,-1):
                area[y][x] += 1
    
paint()
lines.reverse()
paint(0)

count = 0
for y in range(1,1001):
    for x in range(1001):
        if area[y][x] == 2:
            count+=1
print(count)
