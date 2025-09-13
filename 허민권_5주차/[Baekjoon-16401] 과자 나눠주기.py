import sys
input = sys.stdin.readline
M, N = map(int, input().split())
snacks = list(map(int, input().split()))
snacks.sort(reverse=True)  # 내림차순 정렬 (snack < mid 조건 빠른 탈출을 위해)

left = 1
right = snacks[0]
ans = 0

while left <= right:
    mid = (left + right) // 2
    cnt = 0
    # 내림차순 정렬 덕분에, snack이 mid보다 작아지면 이후에도 작으므로 break 가능
    for s in snacks:
        if s < mid:
            break
        cnt += s // mid
        # 조기 탈출: 이미 M개 이상 만들 수 있으면 더이상 계산하지 않음
        if cnt >= M:
            break

    if cnt >= M:
        ans = mid
        left = mid + 1  # 더 긴 길이 시도
    else:
        right = mid - 1

sys.stdout.write(str(ans))
