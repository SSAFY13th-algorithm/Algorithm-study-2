import sys
from collections import deque

input =sys.stdin.readline

N,K = map(int,input().split())

arr = [input() for i in range(N)]
arr.insert(0,0)
s,e=map(int,input().split())


dq = deque()
dq.append(s)
visited = [0] * (N+1)
visited[s] = s
ans = []
while dq:
  cur = dq.popleft()
  if cur == e:
    while visited[e] != e:
      ans.append(e)
      e = visited[e]
    ans.append(s)
    ans.reverse()
    print(*ans)
    break
  
  for next in range(1,N+1):
    if visited[next] != 0: continue
    
    count = 0
    for i in range(K):
      if arr[cur][i] != arr[next][i]:
        count += 1
    if count == 1:
      visited[next] = cur
      dq.append(next)

if len(ans) == 0:
  print(-1)
