import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        int[] students = new int[n+1];
        for(int i=1; i<=n; i++) {
            students[i] = Integer.parseInt(br.readLine());
        }

        int[][] dp = new int[2][n+1];
        dp[0][1] = 1;
        dp[1][1] = 1;

        for(int i=2; i<=n; i++) {
            int max = 0;
            for(int j=1; j<i; j++) {
                if(students[i] < students[j]) continue;
                max = Math.max(max, dp[0][j]);
            }
            dp[0][i] = max+1;
            dp[1][i] = Math.max(dp[0][i], dp[1][i-1]);
        }

        System.out.println(n-dp[1][n]);
    }
}
