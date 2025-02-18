import sys, heapq

input = sys.stdin.readline
# 입력: 격자 한변의 길이 N, 현재 체력 H, 우산 내구도 D
N, H, D = map(int, input().split())
grid = [input().rstrip() for _ in range(N)]

# S(출발), E(안전지대), U(우산) 위치 찾기
S = None
E = None
umbrellas = []
for i in range(N):
    for j in range(N):
        c = grid[i][j]
        if c == 'S':
            S = (i, j)
        elif c == 'E':
            E = (i, j)
        elif c == 'U':
            umbrellas.append((i, j))

# 노드 목록: index 0:S, 1:E, 2이상: 우산들
nodes = []
nodes.append(S)
nodes.append(E)
for umb in umbrellas:
    nodes.append(umb)
total_nodes = len(nodes)

# 모든 노드 쌍에 대해 맨해튼 거리 계산
dist = [[0] * total_nodes for _ in range(total_nodes)]
for i in range(total_nodes):
    r1, c1 = nodes[i]
    for j in range(total_nodes):
        r2, c2 = nodes[j]
        dist[i][j] = abs(r1 - r2) + abs(c1 - c2)

# 체력 소모 누적값은 H-1 이하여야 함 (0이 되면 죽으므로)
health_threshold = H - 1

# 상태: (총 이동 횟수, 누적 체력 소모, 현재 노드, 우산 사용 여부 비트마스크)
# S(노드0)에서는 우산이 없으므로 보호력 p = 0,
# 우산에 도착하면 보호력은 p = D-1 로 재설정됨.
# 우산 노드는 한 번만 사용할 수 있으므로, 비트마스크(우산 index: 노드 2부터)를 사용.
INF = 10**9
dp = {}  # dp[(node, mask)] = 누적 체력 소모의 최소값
pq = []
heapq.heappush(pq, (0, 0, 0, 0))  # (moves, health_used, node, mask)
dp[(0, 0)] = 0

ans = -1
while pq:
    moves, h_used, node, mask = heapq.heappop(pq)
    if dp.get((node, mask), INF) < h_used:
        continue
    # 안전지대(E, 노드1)에 도착했다면 종료
    if node == 1:
        ans = moves
        break

    # 현재 노드에서의 보호력
    # S(노드0)는 p = 0, 우산 노드(인덱스>=2)는 p = D-1
    if node == 0:
        current_prot = 0
    elif node >= 2:
        current_prot = D - 1
    else:
        current_prot = 0  # (노드1는 도착 상태이므로)

    # 다음으로 이동할 노드는 우산(미사용) 또는 안전지대(E)
    for nxt in range(total_nodes):
        if nxt == node:
            continue
        if nxt == 0:
            continue  # S로 돌아갈 필요는 없음.
        # 이미 사용한 우산 노드는 재사용 불가 (노드 인덱스 2 이상)
        if nxt >= 2:
            bit = 1 << (nxt - 2)
            if mask & bit:
                continue
        d = dist[node][nxt]
        if d == 0:
            continue
        # 이동 시 추가 체력 소모 계산
        # [1] S(노드0)에서 출발: extra = d - 1
        # [2] 우산에서 출발(보호력 = D-1): extra = max(0, d - D)
        if node == 0:
            extra = d - 1
        else:
            extra = d - D
            if extra < 0:
                extra = 0
        new_h = h_used + extra
        if new_h > health_threshold:
            continue
        new_moves = moves + d
        # 우산 노드에 도착하면 우산을 획득하므로 비트마스크 업데이트
        if nxt == 1:
            # 안전지대(E)는 우산이 없으므로 상태 업데이트 없이 PQ에 넣음.
            heapq.heappush(pq, (new_moves, new_h, nxt, mask))
        elif nxt >= 2:
            new_mask = mask | (1 << (nxt - 2))
            state = (nxt, new_mask)
            if dp.get(state, INF) > new_h:
                dp[state] = new_h
                heapq.heappush(pq, (new_moves, new_h, nxt, new_mask))
    # end for
# 결과 출력
print(ans if ans != -1 else -1)
