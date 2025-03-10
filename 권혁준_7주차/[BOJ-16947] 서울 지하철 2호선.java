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
	static List<Integer>[] V;
	static int[] par, ans;
	static boolean[] vis;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		N = Integer.parseInt(br.readLine());
		V = new List[N+1];
		ans = new int[N+1];
		par = new int[N+1];
		vis = new boolean[N+1];
		Arrays.fill(par, -1);
		for(int i=1;i<=N;i++) V[i] = new ArrayList<>();
		for(int i=0;i<N;i++) {
			nextLine();
			int a = nextInt(), b = nextInt();
			V[a].add(b);
			V[b].add(a);
		}
		
	}
	
	static void solve() throws Exception{

		par[1] = 0;
		dfs(1, 0);
		for(int i=1;i<=N;i++) bw.write(ans[i] + " ");
		
	}
	
	static void dfs(int n, int p) {
		if(par[n] == -2) return;
		for(int i:V[n]) {
			if(i == p) continue;
			if(par[i] == -1) {
				par[i] = n;
				dfs(i,n);
				continue;
			}
			// cycle
			par[i] = -2;
			int xn = n, xi = i;
			while(xn != -2) {
				xi = xn;
				xn = par[xn];
				par[xi] = -2;
			}
			vis[i] = true;
			bfs(i);
			return;
		}
	}
	
	static void bfs(int n) {
		Queue<int[]> Q = new LinkedList<>();
		Q.add(new int[] {n,0});
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			int from = now[0], time = now[1];
			ans[from] = time;
			for(int to:V[from]) {
				if(vis[to]) continue;
				vis[to] = true;
				Q.add(new int[] {to,par[to] == -2 ? time : time+1});
			}
		}
	}
	
	
}
