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
	
	static int N, M;
	static boolean[] isNotTree, vis;
	
	static int[] root;
	static int find(int x) { return root[x]==x ? x : (root[x]=find(root[x])); }
	
	// 
	
	public static void main(String[] args) throws Exception {
		
		int TC;
		ready();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		for(int tc=1;;tc++) {

			// Write Code

			nextLine();
			N = nextInt();
			M = nextInt();
			if(N == 0) return;
			root = new int[N+1];
			isNotTree = new boolean[N+1];
			vis = new boolean[N+1];
			for(int i=1;i<=N;i++) root[i] = i;
			for(int i=0;i<M;i++) {
				nextLine();
				int x = find(nextInt()), y = find(nextInt());
				if(x == y) isNotTree[x] = true;
				else {
					isNotTree[y] |= isNotTree[x];
					root[x] = y;
				}
			}
			
			//
			
			solve(tc);
			
		}
		
	}
	
	static void solve(int tc) throws Exception{

		// Solve part
		
		int ans = 0;
		for(int i=1;i<=N;i++) {
			int x = find(i);
			if(vis[x]) continue;
			vis[x] = true;
			ans += isNotTree[x] ? 0 : 1;
		}
		
		//
		
		//bw.write("#" + tc + " ");
		
		// Output part
		
		bw.write("Case " + tc + ": ");
		if(ans == 0) bw.write("No trees.\n");
		else if(ans == 1) bw.write("There is one tree.\n");
		else bw.write("A forest of " + ans + " trees.\n");
		
		//
		
	}
	
	

}
