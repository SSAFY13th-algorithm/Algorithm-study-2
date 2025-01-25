import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String dna = br.readLine();

        int[][] dp = new int[n+1][2];

        for(int i=1; i<=n; i++) {
            if(dna.charAt(i-1) == 'A') {
                dp[i][0] = Math.min(dp[i-1][0], dp[i-1][1]+1);
                dp[i][1] = Math.min(dp[i-1][0]+1, dp[i-1][1]+1);
            }
            else {
                dp[i][0] = Math.min(dp[i-1][0]+1, dp[i-1][1]+1);
                dp[i][1] = Math.min(dp[i-1][0]+1, dp[i-1][1]);
            }
        }

        System.out.println(dp[n][0]);
    }
}