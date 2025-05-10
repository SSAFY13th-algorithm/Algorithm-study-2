import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 메인 클래스 - JOB 문자 점프 문제 해결
 * 규칙: J -> O -> B -> J 순서로만 이동 가능, 이동 비용은 거리의 제곱
 */
public class boj12026 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 도달할 수 없는 경우를 표현하는 최대값
    private static final int MAX = 1000000000;

    private static char[] road;    // 문자열(길)을 저장할 배열
    private static int[] dp;       // 각 위치까지의 최소 비용을 저장할 DP 배열
    private static int n, answer;  // 문자열 길이와 정답을 저장할 변수

    /**
     * 메인 메서드
     */
    public static void main(String[] args) throws IOException {
        init();           // 입력 및 초기화
        DP();             // 동적 계획법 실행

        // 마지막 위치의 최소 비용을 정답으로 설정
        answer = dp[n - 1];
        // 불가능한 경우(MAX 값이 그대로인 경우) -1 출력
        if (answer == MAX) answer = -1;

        // 결과 출력 및 자원 정리
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 초기 설정을 하는 메서드
     */
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());   // 문자열 길이 입력
        answer = 0;                            // 정답 초기화

        road = br.readLine().toCharArray();    // 문자열을 문자 배열로 변환
        dp = new int[n];                       // DP 배열 초기화

        // 모든 위치를 일단 도달 불가능한 상태(MAX)로 설정
        Arrays.fill(dp, MAX);
        // 시작 위치(0번 인덱스)의 비용은 0
        dp[0] = 0;
    }

    /**
     * 동적 계획법을 이용해 각 위치까지의 최소 비용을 계산하는 메서드
     * J->O->B->J 순서로만 이동 가능하며, 이동 비용은 거리의 제곱
     */
    private static void DP() {
        for (int i = 1; i < n; i++) {              // 모든 위치에 대해
            for (int j = 0; j < i; j++) {          // 이전 모든 위치에서 현재 위치로 오는 경우 검사
                char prev = getNextChar(road[i]);  // i 위치 이전에 올 수 있는 문자

                // i 위치의 문자가 j 다음에 올 수 있는 문자이고, j 위치에 도달 가능한 경우
                if (road[j] == prev && dp[j] != MAX) {
                    int k = i - j;                              // 두 위치 간 거리
                    dp[i] = Math.min(dp[i], dp[j] + k * k);    // 최소 비용 갱신
                }
            }
        }
    }

    /**
     * 현재 문자 다음에 와야 하는 문자를 반환하는 메서드
     * J -> O -> B -> J 순서를 정의
     */
    private static char getNextChar(char c) {
        if (c == 'J') return 'O';    // J 다음에는 O
        if (c == 'O') return 'B';    // O 다음에는 B
        if (c == 'B') return 'J';    // B 다음에는 J
        return '0';                  // 그 외의 경우 (예외 처리)
    }
}