import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		if(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static int N, M;
	static int[][] A, left, straight, right;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		A = new int[N+1][M+2];
		for(int i=1;i<=N;i++) for(int j=1;j<=M;j++) A[i][j] = nextInt();
		
		left = new int[N+1][M+2];
		straight = new int[N+1][M+2];
		right = new int[N+1][M+2];
		for(int i=1;i<=N;i++) {
			Arrays.fill(left[i], (int)1e9);
			Arrays.fill(straight[i], (int)1e9);
			Arrays.fill(right[i], (int)1e9);
		}
		
	}
	
	static void solve() throws Exception{
		
		int ans = (int)1e9;
		for(int i=1;i<=N;i++) for(int j=1;j<=M;j++) {
			left[i][j] = Math.min(straight[i-1][j+1], right[i-1][j+1]) + A[i][j];
			straight[i][j] = Math.min(left[i-1][j], right[i-1][j]) + A[i][j];
			right[i][j] = Math.min(left[i-1][j-1], straight[i-1][j-1]) + A[i][j];
			if(i == N) {
				ans = Math.min(left[i][j], ans);
				ans = Math.min(straight[i][j], ans);
				ans = Math.min(right[i][j], ans);
			}
		}
		bw.write(ans + "\n");
		
	}
	
}
