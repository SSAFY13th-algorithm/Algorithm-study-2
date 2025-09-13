N, M = map(int, input().split())

arr = []
ans = 0
if N == 1 or M == 1:
    if N == 1:
        arr = [int(i) for i in input()]
    if M == 1:
        arr = [int(input()) for _ in range(N)]
        M = N

    for i in range(1, M):
        for j in range(i + 1, M):
            a, b, c = map(sum, [arr[:i], arr[i:j], arr[j:]])
            ans = max(ans, a * b * c)
    print(ans)
else:
    arr = [[int(i) for i in list(input())] for _ in range(N)]

    arr_sum = [[0] * (M + 1) for _ in range(N + 1)]
    for i in range(1, N + 1):
        for j in range(1, M + 1):
            arr_sum[i][j] = (
                arr[i - 1][j - 1] + arr_sum[i - 1][j] + arr_sum[i][j - 1] - arr_sum[i - 1][j - 1]
            )
    for i in range(2, N + 1):
        for j in range(1, M):
            up, down = arr_sum[i - 1][-1], arr_sum[-1][-1] - arr_sum[i - 1][-1]

            up_left = arr_sum[i - 1][j]
            up_right = up - up_left
            down_left = arr_sum[-1][j] - up_left
            down_right = down - down_left

            ans = max(ans, up_left * up_right * down)
            ans = max(ans, up * down_left * down_right)
            ans = max(ans, (up_left + down_left) * up_right * down_right)
            ans = max(ans, up_left * down_left * (up_right + down_right))

    for i in range(2, N + 1):
        for j in range(i + 1, N + 1):
            top = arr_sum[i - 1][-1]
            middle = arr_sum[j - 1][-1] - top
            bottom = arr_sum[-1][-1] - arr_sum[j - 1][-1]
            ans = max(ans, top * middle * bottom)

    for i in range(2, M + 1):
        for j in range(i + 1, M + 1):
            left = arr_sum[-1][i]
            middle = arr_sum[-1][j] - left
            right = arr_sum[-1][-1] - arr_sum[-1][j]
            ans = max(ans, left * middle * right)

    print(ans)
