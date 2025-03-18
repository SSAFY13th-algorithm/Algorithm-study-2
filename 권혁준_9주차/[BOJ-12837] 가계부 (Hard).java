import java.util.*;
import java.io.*;

class SegTree{
	long[] tree;
	SegTree(int size){
		tree = new long[size*4+1];
	}
	
	void upt(int s, int e, int i, int v, int n) {
		if(s == e) {
			tree[n] += v;
			return;
		}
		int m=(s+e)>>1;
		if(i <= m) upt(s,m,i,v,n*2);
		else upt(m+1,e,i,v,n*2+1);
		tree[n] = tree[n*2] + tree[n*2+1];
	}
	
	long find(int s, int e, int l, int r, int n) {
		if(l>r || l>e || r<s) return 0;
		if(l<=s && e<=r) return tree[n];
		int m=(s+e)>>1;
		return find(s,m,l,r,n*2) + find(m+1,e,l,r,n*2+1);
	}
}

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
	
	static int N, Q;
	static SegTree seg;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		Q = nextInt();
		seg = new SegTree(N);
		
	}
	
	static void solve() throws Exception{
		
		while(Q-->0) {
			int a = nextInt(), b = nextInt(), c = nextInt();
			if(a == 1) seg.upt(1, N, b, c, 1);
			else bw.write(seg.find(1, N, b, c, 1) + "\n");
		}

	}
	
}
