import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] dp = new long[7+n];

        for(int i=1; i<=6; i++) {
            dp[i] = i;
        }

        for(int i=7; i<=n; i++) {
            long max = Integer.MIN_VALUE;
            for(int j=3; j<i; j++) {
                max = Math.max(max, dp[i-j]*(j-1));
            }
            dp[i] = max;
        }

        System.out.println(dp[n]);
    }
}
