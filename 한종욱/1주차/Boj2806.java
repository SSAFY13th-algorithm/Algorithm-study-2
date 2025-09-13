import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Boj2806 {
	// BufferedReader와 BufferedWriter를 사용해 입출력 성능 향상
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	private static int n;                     // 문자열 길이
	private static char[] input;              // 입력 문자열
	private static int[][] dp;                // dp[i][j]: i번째까지 문자열을 처리했을 때, j(0:A, 1:B)로 만드는 최소 비용

	public static void main(String[] args) throws IOException {
	   n = Integer.parseInt(br.readLine());
	   input = br.readLine().toCharArray();
	   dp = new int[n + 1][2];              // dp 배열 초기화

	   // dp 배열을 최댓값으로 초기화 (Math.min 연산을 위해)
	   for (int i = 1; i <= n; i++) {
	       dp[i][0] = dp[i][1] = Integer.MAX_VALUE;
	   }

	   // 첫 번째 문자 처리
	   if (input[0] == 'A') {
	       dp[1][0] = 0;                     // A는 그대로
	       dp[1][1] = 1;                     // A를 B로 변경
	   } else {
	       dp[1][0] = 1;                     // B를 A로 변경
	       dp[1][1] = 0;                     // B는 그대로
	   }

	   // 두 번째 문자부터 순차적으로 처리
	   for (int i = 2; i <= n; i++) {
	       if (input[i - 1] == 'A') {
	           // A를 A로: 이전 A 상태 그대로 또는 이전 B에서 변경
	           dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + 1);
	           // A를 B로: 이전 상태에서 변경 필요
	           dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
	       }
	       if (input[i - 1] == 'B') {
	           // B를 A로: 이전 상태에서 변경 필요
	           dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
	           // B를 B로: 이전 B 상태 그대로 또는 이전 A에서 변경
	           dp[i][1] = Math.min(dp[i - 1][0] + 1, dp[i - 1][1]);
	       }
	   }

	   // 최종적으로 문자열 전체를 A로 만드는 최소 비용 출력
	   bw.write(dp[n][0] + "\n");
	   bw.flush();
	   bw.close();
	   br.close();
	}
}