from itertools import combinations
from bisect import bisect_left
import math

N = int(input())
arr = list(map(int,input().split()))

pos = sorted([i for i in arr if i>0]) 
neg = sorted([i for i in arr if i<0]) 

min_val = math.inf
min_arr = [] # 음수인경우 -붙여서 arr에 넣어야해!

def hello():
    print("hih")

def set_min(n1,n2,n3):
    global min_val, min_arr

    val = abs(n1+n2+n3)
    if min_val > val:
        min_val = val
        min_arr = sorted([n1,n2,n3])


if len(pos) >= 3:
    set_min(pos[0],pos[1],pos[2])
if len(neg) >= 3:
    set_min(-neg[0],-neg[1],-neg[2])

if len(pos)>=2 and len(neg)>=1:
    for (n1,n2) in combinations(pos,2):
        target = bisect_left(neg, n1+n2)

        if target == len(neg):
            set_min(n1, n2, -neg[-1])
        else:
            set_min(n1, n2, -neg[target])
            if target-1 >= 0:
                set_min(n1,n2, -neg[target-1])
    
if len(neg)>=2 and len(pos)>=1:
    for (n1,n2) in combinations(neg,2):
        target = bisect_left(pos, n1+n2)

        if target == len(pos):
            set_min(-n1, -n2, pos[-1])
        else:
            set_min(-n1, -n2, pos[target])
            if target-1 >= 0:
                set_min(-n1,-n2, pos[target-1])

print(*min_arr)
