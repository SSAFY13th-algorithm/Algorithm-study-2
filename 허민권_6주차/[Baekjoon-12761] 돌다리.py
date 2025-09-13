from collections import deque

A,B,N,M= map(int,input().split())

arr = [-1] * (100_001)


def is_ok(pos, cnt):
    if 0<=pos<=100_000 and (arr[pos] == -1 or cnt < arr[pos]):
        return True
    else:
        return False


dq = deque()
arr[N] = 0
dq.append(N)
while dq:
    pos = dq.popleft()

    if pos == M:
        break

    next = [pos+1,pos-1,pos-A,pos+A, pos-B, pos+B,pos*A,pos*B]

    for i in next:
        if is_ok(i, arr[pos]+1):
            arr[i] = arr[pos]+1
            dq.append(i)

print(arr[M])
