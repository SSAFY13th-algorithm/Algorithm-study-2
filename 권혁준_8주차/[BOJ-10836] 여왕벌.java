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

	static int M, N;
	
	static int[][] A, S;
	
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		M = nextInt();
		N = nextInt();
		A = new int[M+1][M+1];
		S = new int[M+2][M+2];
		for(int i=1;i<=M;i++) for(int j=1;j<=M;j++) A[i][j] = 1;
		
	}
	
	static void solve() throws Exception{
		
		while(N-->0) {
			nextLine();
			go(nextInt(), nextInt(), nextInt());
		}
		
		compute();
		for(int i=1;i<=M;i++) {
			for(int j=1;j<=M;j++) bw.write(A[i][j] + " ");
			bw.write("\n");
		}
		
	}
	
	static void go(int a, int b, int c) {
		if(a < M) {
			if(a+b <= M) {
				save(1,2,M,M,2);
				if(c>0 && M-(a+b) >= 1) save(1,1,M-(a+b),1,2);
				if(b>0) save(M-(a+b)+1,1,M-a,1,1);
			}
			else {
				save(1,a+b+2-M,M,M,2);
				save(1,1,M-a,a+b+1-M,1);
				save(M-a+1,2,M,a+b+1-M,1);
			}
		}
		else {
			b += a;
			c += b;
			a -= M-1;
			b -= M-1;
			c -= M-1;
			save(1,a+1,M,b,1);
			save(1,b+1,M,M,2);
		}
	}
	
	static void save(int x1, int y1, int x2, int y2, int v) {
		S[x1][y1] += v;
		S[x2+1][y1] -= v;
		S[x1][y2+1] -= v;
		S[x2+1][y2+1] += v;
	}
	
	static void compute() {
		
		for(int i=1;i<=M;i++) {
			
			int s = 0;
			for(int j=1;j<=M;j++) {
				s += S[i][j];
				S[i+1][j] += S[i][j];
				A[i][j] += s;
				S[i][j] = 0;
			}
		}
		
	}
	
}
