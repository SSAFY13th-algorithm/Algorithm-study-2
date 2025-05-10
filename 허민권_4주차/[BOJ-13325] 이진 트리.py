# 포화 이진 트리의 높이를 입력받음
N = int(input())

# 트리의 높이를 저장
height = N

# 다음 레벨의 노드 가중치를 저장할 리스트
next = [0] * 2**(height-1)

# 현재 레벨의 노드 가중치를 저장할 리스트
cur = [0] * 2**height

# 입력값을 처리할 인덱스
idx = 0

# 왼쪽, 오른쪽 자식 노드 가중치 초기화
left = -1
right = -1

# 최종 결과 (최소한의 가중치 증가량)
answer = 0

# 입력으로 주어진 간선 가중치를 역순으로 처리
for i in reversed(list(map(int, input().split()))):
    # 현재 레벨의 노드를 다 처리했다면, 다음 레벨로 이동
    if idx >= 2**height:
        height -= 1  # 트리 높이 감소
        cur = next  # 현재 레벨을 다음 레벨로 갱신
        next = [0] * 2**(height-1)  # 새로운 다음 레벨 초기화
        idx = 0  # 인덱스 초기화

    # 왼쪽 자식 노드 저장
    if idx % 2 == 0:
        left = i
    else:
        # 오른쪽 자식 노드 저장
        right = i

        # 왼쪽과 오른쪽 노드의 가중치 계산
        left_res = cur[idx-1] // 2 + left
        right_res = cur[idx] // 2 + right

        # 루트에서부터 리프까지의 최대 가중치 계산
        res = max(left_res, right_res)

        # 왼쪽과 오른쪽 간선의 가중치를 맞추기 위해 추가해야 하는 값 계산
        answer += res - cur[idx-1] // 2
        answer += res - cur[idx] // 2

        # 부모 노드의 가중치를 저장
        next[idx // 2] = res * 2

    # 인덱스 증가
    idx += 1

# 결과 출력
print(answer)
