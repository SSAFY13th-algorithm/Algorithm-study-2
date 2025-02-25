N = int(input())
arr = [int(input()) for _ in range(N)]
dp = [1] * N

for i in range(N):
    for j in range(i):
        if arr[j] < arr[i]:
            dp[i] = max(dp[i], dp[j]+1)
print(N-max(dp))
