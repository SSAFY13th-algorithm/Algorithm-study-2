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
	
	static int N, M, K;
	static char[][] A;
	static int[][][] dp;
	static char[] tar;
	
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		K = nextInt();
		A = new char[N][];
		for(int i=0;i<N;i++) A[i] = br.readLine().toCharArray();
		
		tar = br.readLine().toCharArray();
		dp = new int[N][M][tar.length];
		
	}
	
	static void solve() throws Exception{
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(tar[0] == A[i][j]) dp[i][j][0] = 1;
		
		for(int c=1;c<tar.length;c++) {
			for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(tar[c] == A[i][j]) {
				for(int v=1;v<=K;v++) for(int k=0;k<4;k++) {
					int x = i+dx[k]*v, y = j+dy[k]*v;
					if(x<0 || x>=N || y<0 || y>=M) continue;
					dp[i][j][c] += dp[x][y][c-1];
				}
			}
		}
		int ans = 0;
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) ans += dp[i][j][tar.length-1];
		bw.write(ans + "\n");

	}
	
}
