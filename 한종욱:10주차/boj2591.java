import java.io.*;

public class boj2591 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 동적 프로그래밍을 위한 배열
    private static int[] dp;  // dp[i]: i길이까지의 숫자를 해석하는 경우의 수

    // 입력된 숫자 카드의 길이
    private static int n;

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정 및 DP 계산

        // 결과 출력
        bw.write(String.valueOf(dp[n]));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기 데이터를 설정하고 DP를 계산하는 메소드
     * 1~34까지의 숫자로 해석하는 경우의 수를 계산합니다.
     */
    private static void init() throws IOException {
        String input = br.readLine();  // 숫자 카드 입력
        n = input.length();  // 숫자 카드 길이
        dp = new int[n + 1];  // DP 배열 초기화

        // 기본 경우: 빈 문자열은 1가지 방법으로 해석 가능
        dp[0] = 1;

        // 각 위치까지의 해석 방법 수 계산
        for (int i = 1; i <= n; i++) {
            char current = input.charAt(i - 1);  // 현재 숫자

            // 한 자리 숫자로 해석하는 경우 (0이 아닐 때만 가능)
            if (current != '0') {
                dp[i] += dp[i - 1];  // 이전 위치까지의 해석 방법 수를 더함
            }

            // 두 자리 숫자로 해석하는 경우 (두 번째 자리부터 가능)
            if (i > 1) {
                char prev = input.charAt(i - 2);  // 이전 숫자
                int twoDigitNum = (prev - '0') * 10 + (current - '0');  // 두 자리 숫자 계산

                // 두 자리 숫자가 10~34 범위이고 첫 자리가 0이 아닐 때
                if (prev != '0' && twoDigitNum >= 10 && twoDigitNum <= 34) {
                    dp[i] += dp[i - 2];  // 두 자리 전까지의 해석 방법 수를 더함
                }
            }
        }
    }
}