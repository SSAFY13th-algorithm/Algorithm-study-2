import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        int[] chu = new int[n+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int total = 0;
        for(int i=1; i<=n; i++) {
        	chu[i] = Integer.parseInt(st.nextToken());
        	total += chu[i];
        }
        
        int[][] dp = new int[n+1][total+1];
        //1번 추 추가
        dp[1][chu[1]] = 1;
        
        //2번 추부터 ~ n번까지 추가
        for(int i=2; i<=n; i++) {
        	for(int j=0; j<=total; j++) {
        		if(dp[i-1][j] == 0) continue;
        		dp[i][j] = dp[i-1][j];
        		dp[i][j+chu[i]]++;
        		dp[i][Math.abs(j-chu[i])]++;
        	}
        	dp[i][chu[i]]++;
        }
        
        
        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<m; i++) {
        	int a = Integer.parseInt(st.nextToken());
        	if(a <= total && dp[n][a] > 0) sb.append("Y ");
        	else sb.append("N ");
        }
        
        System.out.println(sb.toString().trim());
    }
}
