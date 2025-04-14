import sys
input = sys.stdin.readline

parent = []


def find(a):
  if parent[a] == a:
    return a
  parent[a] = find(parent[a])
  return parent[a]


def union(a,b):
  p_a = find(a)
  p_b = find(b)
  if p_a != p_b:
    parent[p_b] = p_a


for _ in range(int(input())):
  N = int(input())
  nodes = []
  for _ in range(N):
    x,y,r = map(int,input().split())
    nodes.append((x,y,r))
  
  parent = [i for i in range(N)]
  
  for i in range(N):
    for j in range(N):
      if i == j: continue
      
      diff_x = nodes[i][0] - nodes[j][0]
      diff_y = nodes[i][1] - nodes[j][1]

      distance = nodes[i][2] + nodes[j][2]
      if diff_x**2 + diff_y**2 <= distance**2:
        union(i,j)

  print(len(set(find(i) for i in range(N))))
