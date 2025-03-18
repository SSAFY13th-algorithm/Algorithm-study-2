import sys

input = sys.stdin.readline

M, N = map(int, input().split())

arr = [0] * (2 * M - 1)
sub = [0] * (M - 1)

for _ in range(N):
    zero, one, two = map(int, input().split())

    if zero < M:
        if one:
            sub[0] += 1
        if two:
            if zero + one <= M:
                sub[0] += 2 if one == 0 else 1
            else:
                sub[-two] += 2 if one == 0 else 1
    else:
        if one:
            sub[zero - M] += 1
        if two:
            sub[-two] += 2 if one == 0 else 1

    if one:
        arr[zero] += 1
    if two:
        arr[-two] += 2 if one == 0 else 1


first = [1] * (2 * M - 1)
s = 0
for i, v in enumerate(arr):
    s += v
    first[i] += s

col = first[:-M]
first = first[-M:]


row = [1] * (M - 1)
s = 0
for i, v in enumerate(sub):
    s += v
    row[i] += s


print(*first)
row_str = ' '.join(map(str, row))
for _ in range(M-1):
    print(f'{col.pop()} {row_str}')

