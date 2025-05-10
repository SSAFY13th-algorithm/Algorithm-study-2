N = int(input())
A = list(map(int,input().split()))
M = int(input())
B = list(map(int,input().split()))


res = set(A) & set(B)
res = sorted(list(res))

if len(res) == 0:
    print(0)
    exit(0)

idx_a,idx_b = -1,-1
ans = []

while len(res):
    idxes_a = [i for i,x in enumerate(A) if x == res[-1] and idx_a < i]
    idxes_b = [i for i,x in enumerate(B) if x == res[-1] and idx_b < i]

    count = min(len(idxes_a),len(idxes_b))
    if count > 0:
        idx_a = idxes_a[count-1]
        idx_b = idxes_b[count-1]
        ans += [res[-1]] * count
    res.pop()

print(len(ans))
print(*ans)
