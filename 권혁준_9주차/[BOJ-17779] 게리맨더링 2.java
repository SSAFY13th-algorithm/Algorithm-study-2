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
	
	static int N;
	static int[][] A;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		A = new int[N][N];
		for(int i=0;i<N;i++) for(int j=0;j<N;j++) A[i][j] = nextInt();
		
	}
	
	static void solve() throws Exception{
		
		
		
		int ans = (int)1e9;
		for(int x=0;x<N;x++) for(int y=0;y<N;y++) {
			for(int d1=1;y-d1>=0;d1++) for(int d2=1;x+d1+d2<N && y+d2<N;d2++) {
				int[] C = new int[5];
				for(int i=0;i<N;i++) for(int j=0;j<N;j++) {
					if(i<x+d1 && j<=y && i+j < x+y) C[0] += A[i][j];
					else if(i<=x+d2 && j>y && j > i + (y-x)) C[1] += A[i][j];
					else if(i>=x+d1 && j<y-d1+d2 && i-j > x-y+2*d1) C[2] += A[i][j];
					else if(i>x+d2 && j >= y-d1+d2 && i+j > x+y+2*d2) C[3] += A[i][j];
					else C[4] += A[i][j];
				}
				Arrays.sort(C);
				ans = Math.min(ans, C[4]-C[0]);
			}
		}
		bw.write(ans + "\n");

	}
	
}
