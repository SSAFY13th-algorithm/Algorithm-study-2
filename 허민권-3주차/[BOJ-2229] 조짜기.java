import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    static int[][] cache; // DP(메모이제이션) 테이블
    static int[] arr; // 학생들의 점수 배열

    /**
     * 조의 점수 차이를 최대로 만들기 위한 함수 (Top-down DP 방식)
     * @param s 시작 인덱스
     * @param e 끝 인덱스
     * @return 현재 구간 [s, e]에서 조를 나누었을 때의 최대 점수 차이 합
     */
    public static int solve(int s, int e) {
        // 기저 사례: 한 명만 있는 경우, 점수 차이는 0
        if (s == e)
            return 0;

        // 메모이제이션: 이미 계산된 값이면 반환
        if (cache[s][e] != -1)
            return cache[s][e];

        // 구간 내 최대 점수와 최소 점수 초기화
        int maxScore = arr[s];
        int minScore = arr[s];
        int maxDifference = 0; // 최대 점수 차이 합

        // 구간을 나누어 각 조를 형성 (s ~ i), (i+1 ~ e)
        for (int i = s; i < e; i++) {
            // 현재 구간 내 최대, 최소 점수 갱신
            maxScore = Math.max(maxScore, arr[i]);
            minScore = Math.min(minScore, arr[i]);

            // 구간을 나누고 DP로 재귀 호출
            int leftPart = solve(s, i);   // 왼쪽 조의 최적 점수 차이
            int rightPart = solve(i + 1, e); // 오른쪽 조의 최적 점수 차이
            maxDifference = Math.max(maxDifference, leftPart + rightPart);
        }

        // 마지막 구간을 포함한 경우도 고려 (현재 전체 구간 [s, e]를 하나의 조로 취급)
        maxScore = Math.max(maxScore, arr[e]);
        minScore = Math.min(minScore, arr[e]);
        maxDifference = Math.max(maxDifference, maxScore - minScore);

        // 결과를 캐시에 저장 후 반환
        return cache[s][e] = maxDifference;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 학생 수 입력
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        arr = new int[N];  // 학생 점수 배열
        cache = new int[N][N]; // DP 캐시 테이블

        // DP 캐시 초기화 (-1로 설정하여 아직 계산되지 않은 값 표시)
        for (int[] row : cache) {
            Arrays.fill(row, -1);
        }

        // 학생 점수 입력 받기
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 최적의 조 편성 결과 출력
        System.out.println(solve(0, N - 1));
    }
}
