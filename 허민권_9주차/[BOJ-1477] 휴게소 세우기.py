N, M, L = map(int, input().split())
pos = sorted(list(map(int, input().split()))) if N != 0 else []

pos.insert(0, 0)
pos.append(L)
diff = []
for i in range(len(pos) - 1):
    diff.append(pos[i + 1] - pos[i])
diff.sort()

l, r = 1, diff[-1]


while l <= r:

    m = (l + r) // 2

    count = 0
    for d in diff:
        count += (d - 1) // m

    if count <= M:
        r = m - 1
    else:
        l = m + 1

print(l)
