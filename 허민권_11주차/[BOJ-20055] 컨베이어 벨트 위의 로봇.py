from collections import deque
import sys
input = sys.stdin.readline

N, K = map(int, input().split())
belt = deque(map(int, input().split()))
robots = deque([False] * (2 * N))
step = 0

while belt.count(0) < K:
    step += 1
    # 1. 벨트와 로봇 위치 회전
    belt.rotate(1)
    robots.rotate(1)
    # 내리는 위치(N-1)에 로봇이 있으면 제거
    if robots[N-1]:
        robots[N-1] = False

    # 2. 로봇 이동 (내리는 위치 전까지, 뒤에서부터 이동)
    for i in range(N-2, -1, -1):
        if robots[i] and not robots[i+1] and belt[i+1] > 0:
            robots[i] = False
            # 이동한 위치가 내리는 위치라면 로봇을 올리지 않음
            if i+1 != N-1:
                robots[i+1] = True
            belt[i+1] -= 1
    # 이동 후 내리는 위치에 남은 로봇 제거
    if robots[N-1]:
        robots[N-1] = False

    # 3. 올리는 위치(0)에 새로운 로봇 올리기
    if belt[0] > 0 and not robots[0]:
        robots[0] = True
        belt[0] -= 1

print(step)
