import sys
import heapq

input = sys.stdin.readline

N,M = map(int,input().split())

edges = [[] for _ in range(N+1)]

for _ in range(M):
  a,b,c = map(int,input().split())
  edges[a].append((b,c))
  edges[b].append((a,c))

distance = [float('inf')] * (N+1)
distance[1] = 0
heap = [(0,1)]

while heap:
  cost, node = heapq.heappop(heap)

  if node == N:
    print(distance[N])
  
  if cost > distance[node]:
    continue

  for next_node,next_cost in edges[node]:
    new_cost = cost + next_cost
    if new_cost < distance[next_node]:
      distance[next_node] = new_cost
      heapq.heappush(heap, (new_cost, next_node))
