import java.util.*;
import java.io.*;

class Edge{
	int s, e;
	double c;
	Edge(int s, int e, double c){
		this.s = s;
		this.e = e;
		this.c = c;
	}
}

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		while(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static double nextDouble() throws Exception { return Double.parseDouble(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static long[] dp;
	static char[] A;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		A = br.readLine().toCharArray();
		dp = new long[A.length];
		
	}
	
	static void solve() throws Exception{

		if(A[0] != '0') dp[0] = 1;
		for(int i=1;i<A.length;i++) {
			int now = A[i]-'0';
			if(now >= 1) dp[i] += dp[i-1];
			if(A[i-1] != '0' && (A[i-1]-'0')*10 + now <= 34) {
				if(i == 1) dp[i]++;
				else dp[i] += dp[i-2];
			}
		}
		bw.write(dp[A.length-1]+"\n");
		
	}
	
}
