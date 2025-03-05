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

	static int N;
	static long X;
	static long[] len, P;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		nextLine();
		N = nextInt();
		X = nextLong();
		len = new long[N+1];
		P = new long[N+1];
		
	}
	
	static void solve() throws Exception{

		len[0] = 1;
		P[0] = 1;
		for(int i=1;i<=N;i++) {
			len[i] = 2*len[i-1] + 3;
			P[i] = 2*P[i-1] + 1;
		}
		bw.write(rec(N,X) + "\n");
		
		
	}
	
	static long rec(int lev, long x) {
		if(x < 1) return 0;
		if(lev == 0) return 1;
		if(x == 1) return 0;
		if(x == len[lev]) return P[lev];
		if(x <= len[lev-1] + 1) return rec(lev-1, x-1);
		x -= len[lev-1]+2;
		return P[lev-1] + 1 + rec(lev-1,x);
	}
	
}
