import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int[] numbers;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        numbers = new int[n+1];
        for(int i=1; i<=n; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }

        int[][] dp = new int[n+1][m+1];
        Arrays.fill(dp[0], -32768*100);

        dp[0][0] = 0;
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                dp[i][j] = dp[i-1][j];
                for(int k=i-2; k>=0; k--) {
                    if((k+1)/2 < j-1) continue;
                    dp[i][j] = Math.max(dp[i][j], dp[k][j-1]+sum(k+2, i));
                }
                if(j==1) {
                    dp[i][j] = Math.max(dp[i][j], sum(1, i));
                }
            }
        }

        System.out.println(dp[n][m]);
    }

    private static int sum(int start, int end) {
        int sum=0;
        for(int i=start; i<=end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
