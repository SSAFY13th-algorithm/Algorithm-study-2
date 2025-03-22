import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class boj17485 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int[][][] dp;
    private static int[][] arr;
    private static int n, m;


    public static void main(String[] args) throws IOException {
        init();
        DP();

        int answer = (int)1e9;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                answer = Math.min(answer, dp[n - 1][i][j]);
            }
        }
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dp = new int[n][m][3];
        arr = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                dp[0][i][j] = arr[0][i];
            }
        }
    }

    private static void DP() {
        for (int i = 1; i < n; i++) {
            dp[i][0][2] = (int)1e9;
            dp[i][0][1] = arr[i][0] + dp[i - 1][0][0];
            dp[i][0][0] = arr[i][0] + Math.min(dp[i - 1][1][1], dp[i - 1][1][2]);

            for (int j = 1; j < m - 1; j++) {
                dp[i][j][0] = arr[i][j] + Math.min(dp[i - 1][j + 1][1], dp[i - 1][j + 1][2]);
                dp[i][j][1] = arr[i][j] + Math.min(dp[i - 1][j][0], dp[i - 1][j][2]);
                dp[i][j][2] = arr[i][j] + Math.min(dp[i - 1][j - 1][0], dp[i - 1][j - 1][1]);
            }

            dp[i][m - 1][0] = (int)1e9;
            dp[i][m - 1][1] = arr[i][m - 1] + dp[i - 1][m - 1][2];
            dp[i][m - 1][2] = arr[i][m - 1] + Math.min(dp[i - 1][m - 2][0], dp[i - 1][m - 2][1]);
        }
    }
}