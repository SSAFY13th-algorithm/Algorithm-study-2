```java

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
	static Set<Integer>[] V;
	static int[] R;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		V = new TreeSet[N+1];
		for(int i=1;i<=N;i++) V[i] = new TreeSet<>();
		for(int i=1;i<N;i++) {
			int a = nextInt(), b = nextInt();
			V[a].add(b);
			V[b].add(a);
		}
		R = new int[N+1];
		for(int i=1;i<=N;i++) R[i] = nextInt();

	}
	
	static void solve() throws Exception{
		
		Queue<Integer> Q = new LinkedList<>();
		boolean[] vis = new boolean[N+1];
		Q.offer(1);
		vis[1] = true;
		int idx = 2;
		if(R[1] != 1) {
			bw.write("0");
			return;
		}
		while(!Q.isEmpty()) {
			int n = Q.poll();
			for(int i=0;i<V[n].size() - (n == 1 ? 0 : 1);i++) {
				if(idx <= N && !vis[R[idx]] && V[n].contains(R[idx])) {
					Q.offer(R[idx]);
					vis[R[idx]] = true;
					idx++;
				}
			}
		}
		if(idx > N) bw.write("1");
		else bw.write("0");
		
	}
	
}

```
