import java.io.*;
import java.util.*;

public class boj1561 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 놀이기구 별 운행 시간을 저장하는 배열
    private static int[] arr;

    // n: 방문객 수, m: 놀이기구 수, answer: 마지막 방문객이 타게 될 놀이기구 번호
    private static int n, m, answer;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // 이진 탐색으로 마지막 방문객이 놀이기구를 타는 시간과 놀이기구 번호 구하기
        binarySearch();

        // 결과 출력
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력 데이터를 초기화하는 함수
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 방문객 수
        m = Integer.parseInt(st.nextToken());  // 놀이기구 수

        // 각 놀이기구의 운행 시간 입력
        arr = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }

    /**
     * 이진 탐색을 이용해 마지막 방문객이 놀이기구를 타는 시간을 찾는 함수
     */
    private static void binarySearch() {
        long left = 0;                // 최소 시간
        long right = (long)6e14;      // 최대 시간 (충분히 큰 값)

        // 이진 탐색 수행
        while (left <= right) {
            long mid = left + (right - left) / 2;  // 중간값 계산 (오버플로우 방지)

            // mid 시간에 n명을 모두 태울 수 있는지 확인
            if (valid(mid)) {
                right = mid - 1;      // 가능하면 더 작은 시간도 가능한지 확인
            } else {
                left = mid + 1;       // 불가능하면 더 큰 시간이 필요
            }
        }
    }

    /**
     * 주어진 시간(target)에 n명의 방문객을 모두 태울 수 있는지 확인하고,
     * 가능하다면 마지막 방문객이 타게 될 놀이기구 번호를 찾는 함수
     *
     * @param target 검증할 시간
     * @return target 시간에 n명을 모두 태울 수 있으면 true, 아니면 false
     */
    private static boolean valid(long target) {
        // 특수 케이스: n이 m 이하이고 시간이 0이면, 처음에 모든 방문객이 놀이기구에 바로 탑승
        if (n <= m && target == 0) {
            answer = n;  // 마지막 방문객은 n번째 놀이기구에 탑승
            return true;
        }

        // 각 놀이기구가 태울 수 있는 방문객 수와 마지막 방문객 탑승 시간 계산을 위한 배열
        long count[] = new long[m];  // 각 놀이기구가 태울 수 있는 방문객 수
        Arrays.fill(count, 1);       // 초기에 각 놀이기구는 1명씩 태움
        long time[] = new long[m];   // 각 놀이기구의 마지막 방문객 탑승 시간
        long max = 0;                // 가장 늦게 탑승하는 시간
        long totalCount = 0;         // 총 태울 수 있는 방문객 수

        // 각 놀이기구가 target 시간까지 태울 수 있는 방문객 수 계산
        for (int i = 0; i < m; i++) {
            count[i] += target / arr[i];  // target 시간 동안 i번 놀이기구가 추가로 태울 수 있는 방문객 수
            totalCount += count[i];       // 모든 놀이기구가 태울 수 있는 총 방문객 수
        }

        // 모든 놀이기구가 target 시간까지 태울 수 있는 방문객 수가 n보다 작으면 false
        if (totalCount < n)
            return false;

        // target-1 시간까지 태운 방문객 수 계산 (이전 시간까지의 누적)
        long beforeCount = m;  // 초기에 각 놀이기구에 1명씩 타므로 m명
        for (int i = 0; i < m; i++) {
            beforeCount += (target - 1) / arr[i];  // target-1 시간까지 i번 놀이기구가 추가로 태운 방문객 수
            time[i] = (count[i] - 1) * arr[i];     // i번 놀이기구의 마지막 방문객 탑승 시간
            max = Math.max(max, time[i]);          // 가장 늦게 탑승하는 시간 갱신
        }

        // 마지막 방문객이 타게 될 놀이기구 찾기
        long remaining = n - beforeCount;  // target 시간에 새로 탑승하는 방문객 수
        for (int i = 0; i < m; i++) {
            // 마지막에 동시에 운행을 마치는 놀이기구 중에서 번호가 가장 작은 것을 찾음
            if (time[i] == max) {
                remaining--;
                if (remaining == 0) {
                    answer = i + 1;  // 놀이기구 번호는 1부터 시작하므로 +1
                    return true;
                }
            }
        }
        return true;
    }
}