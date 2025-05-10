import java.util.*;
import java.io.*;


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
	
	static int N;
	static int[][] A;
	static int[] r;
	
	static int f(int x) {return x==r[x] ? x : (r[x]=f(r[x]));}
	
	public static void main(String[] args) throws Exception {
		
		for(int T=nextInt();T-->0;) {
			
			ready();
			solve();
		}
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		
		r = new int[N];
		for(int i=0;i<N;i++) r[i] = i;
		
		A = new int[N][3];
		for(int i=0;i<N;i++) for(int j=0;j<3;j++) A[i][j] = nextInt();
		
	}
	
	static void solve() throws Exception{

		for(int i=0;i<N;i++) for(int j=0;j<i;j++) {
			int distSquare = (A[i][0]-A[j][0])*(A[i][0]-A[j][0]) + (A[i][1]-A[j][1])*(A[i][1]-A[j][1]);
			int limitSquare = (A[i][2] + A[j][2])*(A[i][2] + A[j][2]);
			if(distSquare <= limitSquare) {
				int x = f(i), y = f(j);
				if(x != y) r[x] = y;
			}
		}
		
		boolean[] vis = new boolean[N];
		int ans = 0;
		for(int i=0;i<N;i++) {
			int x = f(i);
			if(!vis[x]) {
				vis[x] = true;
				ans++;
			}
		}
		bw.write(ans + "\n");
		
	}
	
}
