import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	
	
	public static void main(String[] args) throws Exception {
		
		nextLine();
		int N = nextInt();
		
		long[] dp = new long[N+1];
		dp[1] = 1;
		for(int i=2;i<=N;i++) {
			dp[i] = dp[i-1] + 1;
			for(int j=3;i-j>=1;j++) {
				dp[i] = Math.max(dp[i], dp[i-j]*(j-1));
			}
		}
		bw.write(dp[N]+"\n");
		
		bwEnd();
	}

}
