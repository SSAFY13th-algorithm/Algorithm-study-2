import sys
from collections import defaultdict
input = sys.stdin.readline


N,M = map(int,input().split())

words = defaultdict(int)

for _ in range(N):
    s = set(input().strip())
    mask = 1<<26
    for i in range(26):
        if chr(97+i) in s:
            mask |= 1<<i
    words[mask] += 1

remember_mask = (1<<27) - 1
for _ in range(M):
    o,x = input().split()
    if o == '1':
        # forgot
        if x in ['a', 'e','i','o','u']:
            continue
        remember_mask = remember_mask & ~(1<<(ord(x)-97))
    else:
        remember_mask |= (1<<(ord(x)-97))
    total = 0
    for word,cnt in words.items():
        if remember_mask == remember_mask | word:
            total += cnt
    print(total)
