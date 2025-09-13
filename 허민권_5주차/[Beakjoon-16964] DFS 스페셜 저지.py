import sys
input = sys.stdin.readline

N = int(input())
graph = [[] for _ in range(N+1)]
for _ in range(N-1):
    a, b = map(int, input().split())
    graph[a].append(b)
    graph[b].append(a)

order = list(map(int, input().split()))
if order[0] != 1:
    print(0)
    sys.exit(0)

# 각 노드가 order에서 몇 번째에 나오는지 저장 (정렬 기준)
pos = [0] * (N+1)
for i, node in enumerate(order):
    pos[node] = i

# 각 노드의 인접 리스트를 order 순서에 맞게 역정렬
for i in range(1, N+1):
    graph[i].sort(key=lambda x: -pos[x])

stack = [1]
visited = [False] * (N+1)
visited[1] = True
idx = 0  # DFS 방문 순서를 비교하기 위한 인덱스

while stack:
    curr = stack.pop()
    # 현재 방문한 노드와 order의 해당 인덱스가 다르면 올바르지 않은 순서
    if order[idx] != curr:
        print(0)
        sys.exit(0)
    idx += 1
    # 스택은 LIFO이므로, order에 맞게 자식 노드를 추가
    for next_node in graph[curr]:
        if not visited[next_node]:
            visited[next_node] = True
            stack.append(next_node)

print(1)
