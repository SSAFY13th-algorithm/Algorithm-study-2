import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        
        int mod = 1000000000;
        
        if(n == 1) {
        	System.out.println(0);
        	return;
        }
        
        long[] dp = new long[n+1];
        dp[2] = 1;
        for(int i=3; i<=n; i++) {
        	dp[i] = (i-1)*(dp[i-1]+dp[i-2])%mod;
        }
        
        System.out.println(dp[n]%mod);
    }
}
