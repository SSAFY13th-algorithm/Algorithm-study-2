DIV = 1_000_000_000
N = int(input())

if N ==1:
  print(0)
elif N == 2:
  print(1)
else:
  res, p1, p2 = 0, 1, 0
  for i in range(2,N):
    res = i * (p1+p2)
    res %= DIV
    p1, p2 = res, p1
  print(res)
