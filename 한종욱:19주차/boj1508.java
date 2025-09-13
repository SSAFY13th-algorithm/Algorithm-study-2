import java.io.*;
import java.util.StringTokenizer;

public class boj1508 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringBuilder sb = new StringBuilder();

    // arr: 심판을 배치할 수 있는 위치들을 저장하는 배열
    private static int[] arr;

    // n: 경주로의 길이
    // m: 배치해야 하는 심판의 수
    // k: 심판을 배치할 수 있는 위치의 개수
    private static int n, m, k;

    public static void main(String[] args) throws IOException {
        init();

        // 이진 탐색으로 심판들 간의 최대 최소 거리를 구함
        int dist = binarySearch();

        // 구한 최소 거리를 바탕으로 실제 심판 배치 결과를 생성
        int index = arr[0]; // 첫 번째 심판은 항상 첫 번째 위치에 배치
        int count = 1;      // 배치된 심판의 수
        sb.append("1");     // 첫 번째 위치에 심판 배치 표시

        // 나머지 위치들에 대해 심판 배치 여부 결정
        for (int i = 1; i < k; i++) {
            // 현재 위치가 마지막으로 배치된 심판과의 거리가 최소 거리 이상이고
            // 아직 배치해야 할 심판이 남아있는 경우
            if (arr[i] - index >= dist && count < m) {
                index = arr[i]; // 마지막 배치 위치 업데이트
                count++;        // 배치된 심판 수 증가
                sb.append("1"); // 현재 위치에 심판 배치
            } else {
                sb.append("0"); // 현재 위치에 심판 배치하지 않음
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터를 읽어서 초기화하는 함수
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 경주로의 길이
        m = Integer.parseInt(st.nextToken()); // 배치해야 하는 심판의 수
        k = Integer.parseInt(st.nextToken()); // 심판을 배치할 수 있는 위치의 개수

        arr = new int[k];
        st = new StringTokenizer(br.readLine());

        // 심판을 배치할 수 있는 위치들을 입력받음
        // 문제에서 이미 정렬되어 주어진다고 가정
        for (int i = 0; i < k; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }

    // 이진 탐색을 통해 심판들 간의 최대 최소 거리를 구하는 함수
    private static int binarySearch() {
        // 탐색 범위 설정
        int left = 1;                    // 최소 거리의 최솟값 (1)
        int right = arr[k - 1] - arr[0]; // 최소 거리의 최댓값 (첫 번째와 마지막 위치의 차이)
        int result = 0;                  // 결과값 저장

        while (left <= right) {
            int mid = left + (right - left) / 2; // 현재 검사할 최소 거리

            // mid 거리로 m명의 심판을 배치할 수 있는지 확인
            if (valid(mid)) {
                // 배치 가능하면 더 큰 거리도 시도해봄
                result = mid;       // 현재 거리를 결과로 저장
                left = mid + 1;     // 더 큰 거리 탐색
            } else {
                // 배치 불가능하면 더 작은 거리로 시도
                right = mid - 1;    // 더 작은 거리 탐색
            }
        }

        return result;
    }

    // 주어진 최소 거리(target)로 m명의 심판을 배치할 수 있는지 확인하는 함수
    private static boolean valid(int target) {
        int count = 1;        // 배치된 심판의 수 (첫 번째는 항상 배치)
        int index = arr[0];   // 마지막으로 배치된 심판의 위치

        // 두 번째 위치부터 확인
        for (int i = 1; i < k; i++) {
            // 현재 위치가 마지막 배치 위치로부터 target 이상 떨어져 있으면
            if (arr[i] - index >= target) {
                count++;            // 심판 배치
                index = arr[i];     // 마지막 배치 위치 업데이트

                // m명을 모두 배치했으면 성공
                if (count == m) return true;
            }
        }

        // 최종적으로 m명 이상 배치했는지 확인
        return count >= m;
    }
}