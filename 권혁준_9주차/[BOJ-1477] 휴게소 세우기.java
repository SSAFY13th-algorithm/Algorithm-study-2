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
	
	static int N, M, L;
	static int[] X;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		L = nextInt();
		X = new int[N+2];
		for(int i=1;i<=N;i++) X[i] = nextInt();
		X[N+1] = L;
		
	}
	
	static void solve() throws Exception{
		
		Arrays.sort(X);
		for(int k=1;k<=L;k++) {
			int cnt = 0;
			for(int i=1;i<N+2;i++) {
				int dist = X[i] - X[i-1];
				int need = (dist-1)/k;
				cnt += need;
			}
			if(cnt <= M) {
				bw.write(k+"\n");
				return;
			}
		}
		
	}
	
}
