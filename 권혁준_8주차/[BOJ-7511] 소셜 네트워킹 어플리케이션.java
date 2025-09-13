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

	static int[] root;
	static int N, M, Q;
	static int tc = 0;
	
	static int find(int x) {return root[x]==x ? x : (root[x]=find(root[x]));}
	
	public static void main(String[] args) throws Exception {
		
		int T = Integer.parseInt(br.readLine());
		while(T-->0) {
			
			ready();
			solve();
		}
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		root = new int[N];
		for(int i=0;i<N;i++) root[i] = i;
		
		while(M-->0) {
			nextLine();
			root[find(nextInt())] = find(nextInt());
		}
		
	}
	
	static void solve() throws Exception{
		
		bw.write("Scenario " + ++tc + ":\n");
		for(Q=Integer.parseInt(br.readLine());Q-->0;) {
			nextLine();
			bw.write(find(nextInt()) == find(nextInt()) ? "1\n" : "0\n");
		}
		bw.write("\n");
		
	}
	
}
