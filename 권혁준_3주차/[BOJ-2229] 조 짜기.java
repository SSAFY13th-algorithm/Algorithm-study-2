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
	static int[] dp;
	static int[] arr;
	static int N;
	

	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
		
		bwEnd();
	}
	
	static void ready() throws Exception{

		N = Integer.parseInt(br.readLine());
		dp = new int[N+1];
		arr = new int[N+1];
		
		nextLine();
		for(int i=1;i<=N;i++) arr[i] = nextInt();
		
	}
	
	static void solve() throws Exception{

		for(int i=1;i<=N;i++) {
			int max = 0, min = 10001;
			for(int j=1;j<=i;j++) {
				max = Math.max(max, arr[i-j+1]);
				min = Math.min(min, arr[i-j+1]);
				dp[i] = Math.max(dp[i], dp[i-j] + max - min);
			}
		}
		
		bw.write(dp[N]+"\n");

	}

	
}
