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

	static int N, M, K, T, ans = 0;
	static char[][] A;
	static int[][][][][] dp;
	static int[] dx = {1,1,1,0,0,-1,-1,-1};
	static int[] dy = {1,0,-1,1,-1,1,0,-1};
	static String s;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		M = nextInt();
		K = nextInt();
		A = new char[N][M];
		for(int i=0;i<N;i++) A[i] = br.readLine().toCharArray();
		
	}
	
	static void solve() throws Exception{
		
		constructDP();
		while(K-->0) {
			s = br.readLine();
			T = s.length();
			ans = 0;

			int a = s.length() > 1 ? s.charAt(1)-'a'+1 : 0;
			int b = s.length() > 2 ? s.charAt(2)-'a'+1 : 0;
			int c = s.length() > 3 ? s.charAt(3)-'a'+1 : 0;
			int d = s.length() > 4 ? s.charAt(4)-'a'+1 : 0;
			
			if(T <= 4) {
				for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(A[i][j] == s.charAt(0)) {
					ans += dp[i][j][a][b][c];
				}
			}
			else {
				for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(A[i][j] == s.charAt(0)){
					for(int k=0;k<8;k++) {
						int x = i+dx[k], y = j+dy[k];
						x = (x+N)%N;
						y = (y+M)%M;
						if(A[x][y] == s.charAt(1)) ans += dp[x][y][b][c][d];
					}
				}
			}
			
			bw.write(ans + "\n");
		}
		
	}
	
	static void constructDP() {
		
		dp = new int[N][M][27][27][27];
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) dp[i][j][0][0][0] = 1;
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) for(int k=0;k<8;k++) {
			int x = i+dx[k], y = j+dy[k];
			x = (x+N)%N;
			y = (y+M)%M;
			dp[i][j][A[x][y]-'a'+1][0][0] += dp[x][y][0][0][0];
		}
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) for(int k=0;k<8;k++) {
			int x = i+dx[k], y = j+dy[k];
			x = (x+N)%N;
			y = (y+M)%M;
			for(int a=1;a<=26;a++) dp[i][j][A[x][y]-'a'+1][a][0] += dp[x][y][a][0][0];
		}
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) for(int k=0;k<8;k++) {
			int x = i+dx[k], y = j+dy[k];
			x = (x+N)%N;
			y = (y+M)%M;
			for(int a=1;a<=26;a++) for(int b=1;b<=26;b++) dp[i][j][A[x][y]-'a'+1][a][b] += dp[x][y][a][b][0];
		}
		
	}
	
}
