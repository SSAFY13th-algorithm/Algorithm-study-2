import java.io.*;

public class boj1947 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int MOD = 1000000000;  // 나머지 연산을 위한 모듈러 값
    private static long[] dp;  // 완전 순열의 경우의 수를 저장할 배열
    private static int n;      // 사람의 수

    public static void main(String[] args) throws IOException {
        init();  // 초기 설정 및 DP 계산
        bw.write(String.valueOf(dp[n]));  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 완전 순열(어느 누구도 자신의 선물을 받지 않는 순열)의 경우의 수 계산
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 사람 수 입력
        dp = new long[n + 1];  // DP 배열 초기화

        // 기본 케이스 설정
        dp[0] = 1;  // 0명일 때는 1가지 경우 (아무도 없는 경우)
        dp[1] = 0;  // 1명일 때는 0가지 경우 (자신의 선물을 받지 않을 방법 없음)

        // 점화식을 이용한 DP 계산
        // 완전 순열의 점화식: D(n) = (n-1) * (D(n-1) + D(n-2))
        for (int i = 2; i <= n; i++) {
            /*
             * 이 점화식의 의미:
             * 1. i번째 사람이 j번째 사람에게 선물을 줄 때(j != i)
             * 2. 두 가지 경우가 발생:
             *    a. j번째 사람이 i번째 사람에게 선물을 줌 -> 나머지 n-2명에 대한 완전 순열 문제(dp[i-2])
             *    b. j번째 사람이 i번째 사람에게 선물을 주지 않음 -> i와 j를 제외한 나머지에 대한 완전 순열(dp[i-1])
             * 3. 이를 j의 n-1가지 경우에 대해 모두 계산
             */
            dp[i] = (i-1)*(dp[i-1] + dp[i-2]) % MOD;  // 오버플로우 방지를 위한 모듈러 연산
        }
    }
}