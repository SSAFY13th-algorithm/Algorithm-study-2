import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj11058 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long[] dp;     // dp[i]: i번 눌렀을 때 얻을 수 있는 최대 문자 개수
    private static int n;         // 키를 누르는 횟수

    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());   // 키를 누르는 횟수 입력
        dp = new long[n + 1];                  // dp 배열 초기화
        dp();                                  // dp 계산

        // 결과 출력 및 스트림 닫기
        bw.write(dp[n] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void dp() {
        // i: 현재까지 누른 키의 횟수
        for (int i = 1; i <= n; i++) {
            // 기본적으로는 A를 하나 추가하는 경우
            dp[i] = dp[i - 1] + 1;

            // Ctrl+A, Ctrl+C, Ctrl+V를 사용할 수 있는 경우 (최소 7번 이상 눌러야 함)
            if (i > 6) {
                // j: 복사-붙여넣기를 반복하는 횟수
                for (int j = 1; j < i - 3; j++) {
                    // dp[i-j-2]: Ctrl+A, Ctrl+C를 누르기 전까지의 상태
                    // (j+1): 복사된 내용이 j번 붙여넣어지므로 j+1배가 됨
                    dp[i] = Math.max(dp[i], dp[i - j - 2] * (j + 1));
                }
            }
        }
    }
}