import sys

input = sys.stdin.readline

T = 1
while True:
    N, M = map(int, input().split())

    if N == 0 and M == 0:
        break

    nodes = [[] for _ in range(N + 1)]
    visited = [False] * (N + 1)
    for _ in range(M):
        a, b = map(int, input().split())
        nodes[a].append(b)
        nodes[b].append(a)

    count = 0
    for root in range(1, N + 1):
        if visited[root]:
            continue
        s = [[(-1, root)]]
        visited[root] = True
        isTree = True
        while len(s) > 0:
            # print("cur: ", s)
            if len(s[-1]) == 0:
                s.pop()
                continue
            (prev, cur) = s[-1].pop()

            next_nodes = []

            for next in nodes[cur]:
                if next == prev:
                    continue

                if visited[next]:
                    isTree = False
                else:
                    visited[next] = True
                    next_nodes.append((cur, next))
            s.append(next_nodes)
        if isTree:
            count += 1
        # print(f"rootnode: {root}", isTree)

    ans = ""
    if count == 0:
        ans = "No trees."
    elif count == 1:
        ans = "There is one tree."
    else:
        ans = f"A forest of {count} trees."

    print(f"Case {T}: {ans}")
    T += 1
