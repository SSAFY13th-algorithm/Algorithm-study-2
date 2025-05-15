import java.io.*;
import java.util.*;

public class boj17951 {
    // 입출력 스트림 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] arr; // 입력 배열
    private static int n, k;  // n: 배열 크기, k: 그룹 수

    public static void main(String[] args) throws IOException {
        init();  // 입력 데이터 초기화
        int answer = binarySearch();  // 이분 탐색으로 최적값 찾기
        bw.write(answer + "\n");  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 함수
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 배열 크기 입력
        k = Integer.parseInt(st.nextToken());  // 그룹 수 입력

        arr = new int[n];  // 배열 초기화

        // 배열 요소 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }

    // 이분 탐색 함수 - 최적의 최소값 찾기
    private static int binarySearch() {
        int left = 0;  // 최소 가능한 값
        int right = 2000000;  // 최대 가능한 값 (문제 제약 조건에 따라 설정)
        int result = -1;  // 결과값 초기화

        while (left <= right) {
            int mid = left + (right - left) / 2;  // 중간값 계산 (오버플로우 방지용 계산법)

            // mid 값이 유효한지 확인
            if (valid(mid)) {
                result = mid;  // 현재 mid가 가능하면 결과 갱신
                left = mid + 1;  // 더 큰 값을 찾기 위해 왼쪽 경계 이동 (최대값을 찾는 이분 탐색)
            } else {
                right = mid - 1;  // 불가능하면 더 작은 값 탐색
            }
        }

        return result;  // 최종 결과 반환
    }

    // 특정 최소값(target)으로 k개 이상의 그룹을 만들 수 있는지 확인하는 함수
    private static boolean valid(int target) {
        int count = 1;  // 그룹 수 카운트 (첫 번째 그룹부터 시작)
        int value = 0;  // 현재 그룹의 합계

        // 배열을 순회하며 그룹 나누기
        for (int i = 0; i < n; i++) {
            value += arr[i];  // 현재 요소를 그룹에 추가

            // 현재 그룹의 합이 목표값 이상이면 새 그룹 시작
            if (value >= target) {
                value = 0;  // 합계 초기화
                count++;  // 그룹 수 증가
            }
        }

        // 마지막 그룹의 합이 target보다 작고 정확히 k개의 그룹이 만들어졌다면 유효하지 않음
        // (마지막 그룹이 target에 도달하지 못했지만 더 이상 그룹을 만들 수 없는 경우)
        if (value < target && count == k) return false;

        // k개 이상의 그룹을 만들 수 있으면 유효
        return count >= k;
    }
}