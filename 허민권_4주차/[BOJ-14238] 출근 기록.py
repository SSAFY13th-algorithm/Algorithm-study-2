import sys
from functools import lru_cache

sys.setrecursionlimit(10000)
S = sys.stdin.readline().strip()
# 각 직원의 등장 횟수 계산
count_A = S.count("A")
count_B = S.count("B")
count_C = S.count("C")


# DFS에서 state로 (남은 A, 남은 B, 남은 C, 직전 출근자, 그 전 출근자)를 관리합니다.
# 초기 state에서는 직전 출근자와 그 전 출근자를 빈 문자열("")로 처리합니다.
@lru_cache(maxsize=None)
def dfs(a, b, c, last1, last2):
    # 모든 문자를 사용했으면 valid한 순서이므로 빈 문자열 반환
    if a == 0 and b == 0 and c == 0:
        return ""
    # A는 제약없이 언제든 출근 가능
    if a > 0:
        res = dfs(a - 1, b, c, "A", last1)
        if res is not None:
            return "A" + res
    # B는 직전 출근자가 B면 안됨
    if b > 0:
        if last1 != "B":  # 만약 어제 B였다면 오늘 B 불가
            res = dfs(a, b - 1, c, "B", last1)
            if res is not None:
                return "B" + res
    # C는 직전 두 날 중 어느 날이라도 C가 있으면 안됨.
    if c > 0:
        if last1 != "C" and last2 != "C":
            res = dfs(a, b, c - 1, "C", last1)
            if res is not None:
                return "C" + res
    return None


answer = dfs(count_A, count_B, count_C, "", "")
if answer is None:
    sys.stdout.write("-1")
else:
    sys.stdout.write(answer)
