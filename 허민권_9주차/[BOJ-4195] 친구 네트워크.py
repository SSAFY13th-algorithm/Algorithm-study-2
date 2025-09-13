from collections import defaultdict
import sys

input = sys.stdin.readline


def find(p):
    while pos[p] != p:
        p = pos[p]
    return p


for _ in range(int(input())):
    friend = defaultdict(int)
    idx = 1
    pos = [-1]
    count = [-1]
    for _ in range(int(input())):
        a, b = input().split()

        if not friend[a]:
            friend[a] = idx
            idx += 1
            pos.append(len(pos))
            count.append(1)

        if not friend[b]:
            friend[b] = idx
            idx += 1
            pos.append(len(pos))
            count.append(1)

        p1 = find(friend[a])
        p2 = find(friend[b])
        if p1 == p2:
            print(count[p1])
            continue

        l = min(p1, p2)
        r = max(p1, p2)
        pos[r] = l
        count[l] += count[r]

        print(count[l])
