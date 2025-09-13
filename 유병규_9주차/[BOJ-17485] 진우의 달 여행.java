import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        int[][] map = new int[n][m];
        for(int i=0; i<n; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<m; j++) {
        		map[i][j] = Integer.parseInt(st.nextToken());
        	}
        }
        
        int[][] d = {{-1,-1},{-1,0},{-1,1}};
        
        int[][][] dp = new int[n][m][3];
        for(int i=0;i<m; i++) {
        	for(int dir=0; dir<3; dir++) {
        		dp[0][i][dir] = map[0][i];
        	}
        }
        
        for(int i=1; i<n; i++) {
        	for(int j=0; j<m; j++) {
        		for(int index=0; index<3; index++) {
        			int min = Integer.MAX_VALUE;
        			int px = i+d[index][0];
        			int py = j+d[index][1];
        			if(px<0 || py<0 || py>=m) continue;
        			for(int k=0; k<3; k++) {
        				if(index==k) continue;
        				if(dp[px][py][k] == 0) continue;
        				min = Math.min(min, dp[px][py][k]);
        			}
        			if(min == Integer.MAX_VALUE) min = 0;
        			dp[i][j][index] = min+map[i][j];
        		}
        	}
        }
        
        int result = Integer.MAX_VALUE;
        for(int i=0; i<m; i++) {
        	for(int j=0; j<3; j++) {
        		if(dp[n-1][i][j] == 0) continue;
        		result = Math.min(result, dp[n-1][i][j]);
        	}
        }
        System.out.println(result);
    }
}
