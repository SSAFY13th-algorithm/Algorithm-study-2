import sys
from bisect import bisect_left, bisect_right

input = sys.stdin.readline

while True:
    arr = input().split()  # 한 줄 입력을 받아 공백 기준으로 나누어 리스트로 저장
    N = int(arr[0])  # 첫 번째 값은 사용될 숫자의 개수
    arr = arr[1:]  # 나머지 숫자들 리스트로 저장
    
    if N == 0:  # 입력이 0이면 프로그램 종료
        break
    
    arr.sort()  # 숫자들을 정렬 (문자열 기준 정렬)
    
    # 입력된 숫자의 개수가 홀수인지 판별
    is_odd = True if len(arr) % 2 == 1 else False
    
    # '0'의 개수 찾기 (bisect 사용하여 빠르게 개수 확인)
    zero = bisect_right(arr, '0') - bisect_left(arr, '0')
    
    # 첫 번째 숫자와 두 번째 숫자 선택 (앞에서 두 개 선택)
    num1 = arr[zero]  # 첫 번째 숫자 (0이 아닌 가장 작은 수)
    num2 = arr[zero + 1]  # 두 번째 숫자
    
    # 사용한 두 개 숫자를 제외한 나머지 리스트 구성
    arr = ['0'] * zero + arr[zero+2:]
    
    # 숫자가 홀수 개라면 num1에 가장 작은 숫자 추가
    if is_odd:
        num1 += arr[0]
        arr = arr[1:]  # 사용한 숫자를 리스트에서 제거
    
    # 나머지 숫자들을 번갈아가며 num1과 num2에 배분
    for i in range(len(arr)):
        if i % 2 == 0:
            num1 += arr[i]
        else:
            num2 += arr[i]
    
    # 두 숫자의 합을 출력
    print(int(num1) + int(num2))
