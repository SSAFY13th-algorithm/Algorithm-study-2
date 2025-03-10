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

	static int N;
	static int[] dp;
	static char[] A;
	static final int INF = (int)1e9;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		N = Integer.parseInt(br.readLine());
		A = br.readLine().toCharArray();
		dp = new int[N];
		Arrays.fill(dp, INF);
		
	}
	
	static void solve() throws Exception{

		dp[0] = 0;
		for(int i=1;i<N;i++) {
			char tar;
			if(A[i] == 'B') tar = 'J';
			else if(A[i] == 'O') tar = 'B';
			else tar = 'O';
			
			for(int j=0;j<i;j++) if(A[j] == tar) {
				dp[i] = Math.min(dp[i], dp[j] + (i-j)*(i-j));
			}
		}
		bw.write((dp[N-1] == INF ? -1 : dp[N-1]) + "\n");
		
	}
	
}
