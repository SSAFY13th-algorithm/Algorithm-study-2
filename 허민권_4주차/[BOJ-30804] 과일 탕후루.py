# 첫 번째 입력: 과일의 개수 N
N = int(input())

# 두 번째 입력: 탕후루에 꽂힌 과일 리스트
arr = list(map(int, input().split()))

# 과일별로 가장 앞과 뒤의 인덱스를 저장할 딕셔너리
fruits = {}

# 배열을 순회하며 각 과일이 등장하는 첫 번째와 마지막 위치 저장
for i, s in enumerate(arr):
    if s not in fruits:
        # 새로운 과일이면 처음 등장한 위치를 시작점과 끝점으로 설정
        fruits[s] = [i, i]
    else:
        # 기존 과일이면 끝점만 갱신
        fruits[s][1] = i

# 과일 종류가 2개 이하라면 전체 배열의 길이가 최대 개수가 됨
if len(fruits) <= 2:
    print(len(arr))
else:
    ans = 2  # 최대 과일 개수 (최소 두 종류의 과일은 선택 가능)

    # 두 종류의 과일을 선택하는 모든 조합을 확인
    for i, f1 in fruits.items():
        for j, f2 in fruits.items():
            # 같은 과일끼리는 비교할 필요 없음
            if i == j:
                continue
            
            # 두 과일의 최소 시작점과 최대 끝점 계산
            s = min(f1[0], f2[0])
            e = max(f1[1], f2[1])

            cnt = 0  # 연속된 과일 개수를 세기 위한 변수
            for k in range(s, e + 1):
                # 선택한 두 종류의 과일이 아니면 연속 개수 초기화
                if arr[k] != i and arr[k] != j:
                    cnt = 0
                else:
                    # 선택한 두 종류의 과일이면 연속 개수 증가
                    cnt += 1
                
                # 최대 과일 개수 갱신
                ans = max(ans, cnt)

    # 결과 출력: 가장 긴 두 종류 이하의 연속된 과일 개수
    print(ans)
