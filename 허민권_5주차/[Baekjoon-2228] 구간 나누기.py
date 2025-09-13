import sys, math

input = sys.stdin.readline

N, M = map(int, input().split())
arr = [int(input()) for _ in range(N)]

# dp[m][j]: 첫 j+1개의 원소에서 정확히 m개의 구간 선택시 최대 합
dp = [[-math.inf] * N for _ in range(M + 1)]

# m = 1 인 경우, 일반적인 연속합 (최대 부분합) 문제
local = arr[0]
dp[1][0] = arr[0]
for j in range(1, N):
    local = max(local + arr[j], arr[j])
    dp[1][j] = max(dp[1][j - 1], local)

# m >= 2 인 경우, 새로운 구간은 바로 앞 구간과 인접하면 안 되므로 j-2를 사용
for m in range(2, M + 1):
    # m개의 구간을 만들기 위한 최소 인덱스는 2*m - 2 (0-indexed)
    start = 2 * m - 2
    local = dp[m - 1][start - 2] + arr[start]
    dp[m][start] = local
    for j in range(start + 1, N):
        # option1: 이전 구간(현재 m번째 구간)을 이어서 확장
        option1 = local + arr[j]
        # option2: j번째 원소를 새로운 구간의 시작으로 선택 (j-2까지의 m-1 구간 최적해와 연결)
        option2 = dp[m - 1][j - 2] + arr[j] if j - 2 >= 0 else -math.inf
        local = max(option1, option2)
        dp[m][j] = max(dp[m][j - 1], local)

print(dp[M][N - 1])
