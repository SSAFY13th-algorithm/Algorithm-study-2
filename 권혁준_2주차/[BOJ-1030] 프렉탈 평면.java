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
	static int N, K;
	
	static boolean isMid(int x) {
		return ((N-K)/2 <= x && x < (N/2 + (K+1)/2));
	}
	
	static char solve(int size, int r, int c) {
		if(size == 1) return '0';
		int x = r / (size/N), y = c / (size/N);
		if(isMid(x) && isMid(y)) return '1';
		return solve(size/N, r%(size/N), c%(size/N));
	}

	public static void main(String[] args) throws Exception {

		nextLine();
		int S = nextInt();
		N = nextInt();
		K = nextInt();
		int r1 = nextInt(), r2 = nextInt(), c1 = nextInt(), c2 = nextInt();
		
		int size = 1;
		for(int i=0;i<S;i++) size *= N;
		for(int i=r1;i<=r2;i++) {
			for(int j=c1;j<=c2;j++) bw.write(solve(size,i,j));
			bw.write("\n");
		}
		
		bwEnd();
	}

}
