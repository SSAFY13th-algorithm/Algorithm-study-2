import java.util.*;
import java.io.*;

class Node{
	int attacks = 0;
	int mine = 0;
	Node(int attacks, int mine){
		this.attacks = attacks;
		this.mine = mine;
	}
}

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
	static long[] A;
	static long res = Long.MAX_VALUE;
	static long a, b, c;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		N = Integer.parseInt(br.readLine());
		A = new long[N];
		nextLine();
		for(int i=0;i<N;i++) A[i] = nextInt();
		
	}
	
	static void solve() throws Exception{
		
		Arrays.sort(A);
		for(int i=0;i<N;i++) for(int j=i+1;j<N;j++) {
			long tar = -(A[i] + A[j]);
			int x = lowerBound(j+1, N, tar);
			if(x-1 >= 0 && x-1 != j) check(A[i], A[j], A[x-1]);
			if(0 <= x && x < N) check(A[i], A[j], A[x]);
		}
		bw.write(a + " " + b + " " + c);
		
		
	}
	
	static void check(long x, long y, long z) {
		if(Math.abs(x+y+z) < res) {
			res = Math.abs(x+y+z);
			a = x;
			b = y;
			c = z;
		}
	}
	
	static int lowerBound(int s, int e, long x) {
		int m = (s+e)>>1;
		while(s<e) {
			if(A[m] < x) s = m+1;
			else e = m;
			m = (s+e)>>1;
		}
		return m;
	}
}
