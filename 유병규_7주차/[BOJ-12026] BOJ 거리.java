import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        String line = br.readLine();
        int[] dp = new int[line.length()+1];
        
        for(int i=2; i<=n; i++) {
        	if(line.charAt(i-1) == 'B') {
        		int min = Integer.MAX_VALUE;
        		for(int j=1; j<i; j++) {
        			if(line.charAt(j-1) != 'J' || dp[j] == -1) continue;
        			min = Math.min(min, dp[j]+((i-j)*(i-j)));
        		}
        		dp[i] = min==Integer.MAX_VALUE ? -1 : min;
        	}
        	
        	else if(line.charAt(i-1) == 'O') {
        		int min = Integer.MAX_VALUE;
        		for(int j=1; j<i; j++) {
        			if(line.charAt(j-1) != 'B' || dp[j] == -1) continue;
        			min = Math.min(min, dp[j]+((i-j)*(i-j)));
        		}
        		dp[i] = min==Integer.MAX_VALUE ? -1 : min;
        	}
        	
        	else{
        		int min = Integer.MAX_VALUE;
        		for(int j=1; j<i; j++) {
        			if(line.charAt(j-1) != 'O' || dp[j] == -1) continue;
        			min = Math.min(min, dp[j]+((i-j)*(i-j)));
        		}
        		dp[i] = min==Integer.MAX_VALUE ? -1 : min;
        	}
        }
        
//        System.out.println(Arrays.toString(dp));
        System.out.println(dp[n]);
    }
}
