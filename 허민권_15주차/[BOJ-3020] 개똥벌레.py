from bisect import bisect_left
import sys
from collections import defaultdict

input = sys.stdin.readline
N, H = map(int, input().split())

top = []
bottom = []

for i in range(N):
    if i%2==0:
        bottom.append(int(input()))
    else:
        top.append(int(input()))

bottom.sort()
top.sort()



left,right = 1, H

count = defaultdict(int)
min_ans = N
for i in range(1, H+1):

    b_cnt = len(bottom) - bisect_left(bottom,i)
    t_cnt = len(top) - bisect_left(top, H-i+1)
    total = b_cnt + t_cnt
    min_ans = min(min_ans,total)
    count[total] += 1
print(min_ans, count[min_ans])
