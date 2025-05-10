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
	
	public static void main(String[] args) throws Exception {
		
		for(int T=nextInt();T-->0;) {
			
			ready();
			solve();
		}
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		
		A = new int[N][3];
		for(int i=0;i<N;i++) for(int j=0;j<3;j++) A[i][j] = nextInt();
		
	}
	
	static void solve() throws Exception{

		boolean[] vis = new boolean[N];
		int ans = 0;
		for(int i=0;i<N;i++) if(!vis[i]) {
			ans++;
			Queue<Integer> Q = new LinkedList<>();
			vis[i] = true;
			Q.add(i);
			while(!Q.isEmpty()) {
				int n = Q.poll();
				for(int j=0;j<N;j++) if(!vis[j]) {
					int distSquare = (A[n][0]-A[j][0])*(A[n][0]-A[j][0]) + (A[n][1]-A[j][1])*(A[n][1]-A[j][1]);
					int limitSquare = (A[n][2] + A[j][2])*(A[n][2] + A[j][2]);
					if(distSquare <= limitSquare) {
						vis[j] = true;
						Q.add(j);
					}
				}
			}
		}
		bw.write(ans + "\n");
		
	}
	
}
