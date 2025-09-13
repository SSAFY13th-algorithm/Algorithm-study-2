from bisect import bisect_left  # 이진 탐색을 위한 bisect 모듈 사용
import math  # 무한대 값 설정을 위해 math 모듈 사용

input()  # 첫 번째 줄(플레이어가 받은 숫자 카드 개수)을 입력받지만 사용하지 않음

# 각 플레이어가 받은 숫자 카드를 입력받아 중복을 제거하고 정렬
a = sorted(list(set(map(int, input().split()))))
b = sorted(list(set(map(int, input().split()))))
c = sorted(list(set(map(int, input().split()))))

ans = math.inf  # 최소 벌점을 저장할 변수, 초기값은 무한대

# 첫 번째 플레이어의 모든 카드에 대해 반복
for i in a:
    # 두 번째 플레이어가 선택할 수 있는 최적의 카드 후보 찾기
    cardb = []
    idx = bisect_left(b, i)  # i 이상인 첫 번째 위치 찾기
    if idx == len(b):  # i보다 큰 값이 없는 경우
        cardb.append(b[idx - 1])
    elif idx == 0:  # i보다 작은 값이 없는 경우
        cardb.append(b[idx])
    else:  # i보다 크거나 같은 값과 바로 작은 값 둘 다 후보로 추가
        cardb.append(b[idx])
        cardb.append(b[idx - 1])
    
    # 세 번째 플레이어가 선택할 수 있는 최적의 카드 후보 찾기
    cardc = []
    idx = bisect_left(c, i)  # i 이상인 첫 번째 위치 찾기
    if idx == len(c):  # i보다 큰 값이 없는 경우
        cardc.append(c[idx - 1])
    elif idx == 0:  # i보다 작은 값이 없는 경우
        cardc.append(c[idx])
    else:  # i보다 크거나 같은 값과 바로 작은 값 둘 다 후보로 추가
        cardc.append(c[idx])
        cardc.append(c[idx - 1])
    
    # 두 번째와 세 번째 플레이어의 가능한 카드 조합을 고려하여 최소 벌점 계산
    for j in cardb:
        for k in cardc:
            ans = min(ans, max(i, j, k) - min(i, j, k))  # 현재까지의 최소 벌점 갱신

print(ans)  # 최소 벌점 출력
