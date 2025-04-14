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
	
	static int N, M, R;
	static int[] T;
	static int[][] dist;
	
	static final int INF = (int)1e9;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		R = nextInt();
		T = new int[N+1];
		for(int i=1;i<=N;i++) T[i] = nextInt();
		
		dist = new int[N+1][N+1];
		for(int i=0;i<=N;i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}
		for(int i=0;i<R;i++) {
			int a = nextInt(), b = nextInt(), c = nextInt();
			dist[a][b] = Math.min(dist[a][b], c);
			dist[b][a] = Math.min(dist[b][a], c);
		}
		
	}
	
	static void solve() throws Exception{
		
		for(int i=1;i<=N;i++) for(int j=1;j<=N;j++) for(int k=1;k<=N;k++) dist[j][k] = Math.min(dist[j][k], dist[j][i] + dist[i][k]);
		
		int ans = 0;
		for(int i=1;i<=N;i++) {
			int res = 0;
			for(int j=1;j<=N;j++) if(dist[i][j] <= M) res += T[j];
			ans = Math.max(ans, res);
		}
		bw.write(ans + "\n");

	}
	
}
