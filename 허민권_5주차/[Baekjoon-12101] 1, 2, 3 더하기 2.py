N, K = map(int, input().split())

result = []
count = 0  # 몇 번째 경우인지 카운트


def backtrack(total, path):
    global count

    if total == N:
        count += 1
        if count == K:
            print("+".join(map(str, path)))
            exit()
        return

    if total > N:
        return

    for num in [1, 2, 3]:
        backtrack(total + num, path + [num])


# 백트래킹 실행
backtrack(0, [])

# K번째 경우가 없는 경우 -1 출력
print(-1)
