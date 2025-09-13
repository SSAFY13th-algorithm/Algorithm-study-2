import sys
sys.setrecursionlimit(10**5)
input = sys.stdin.readline

N = int(input())
nodes = [[] for _ in range(N+1)] # 노드 연결
state = [(-1,-1)] * (N+1) # 노드 상태
sub= [0] * (N+1) # 양 숫자

def dfs(cur,prev):
    who, count = state[cur]

    for next in nodes[cur]:
        if next == prev: continue
        dfs(next,cur)

    
    if who == 'S':
        for child in nodes[cur]:
            if child == prev: continue
            sub[cur] += sub[child]
        sub[cur] += count
    else:
        for child in nodes[cur]:
            if child == prev: continue
            sub[cur] += sub[child]
        sub[cur] = max(sub[cur]-count, 0)
    # print(f'cur: {cur}, sub: {sub[cur]}')


for i in range(2,N+1):
    who,count,parent = input().strip().split(' ')
    count = int(count)
    parent = int(parent)
    
    nodes[i].append(parent)
    nodes[parent].append(i)
    state[i] = (who,count)

dfs(1,-1)
ans = 0
for child in nodes[1]:
    ans += sub[child]
print(ans)
