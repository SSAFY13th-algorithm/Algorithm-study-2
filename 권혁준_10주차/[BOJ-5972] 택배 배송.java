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
	
	static int N, M;
	static List<int[]>[] V;
	static final int INF = (int)1e9;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		V = new List[N+1];
		for(int i=1;i<=N;i++) V[i] = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			int a = nextInt(), b = nextInt(), c = nextInt();
			V[a].add(new int[] {b,c});
			V[b].add(new int[] {a,c});
		}
		
	}
	
	static void solve() throws Exception{

		int[] D = new int[N+1];
		Arrays.fill(D, INF);
		PriorityQueue<int[]> Q = new PriorityQueue<>((a,b) -> a[0]-b[0]);
		Q.offer(new int[] {0,1});
		
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			int d = now[0], n = now[1];
			if(d>D[n]) continue;
			if(n == N) {
				bw.write(d + "\n");
				return;
			}
			for(int[] e:V[n]) {
				int i = e[0], c = e[1];
				if(D[i] > d+c) {
					D[i] = d+c;
					Q.offer(new int[] {D[i],i});
				}
			}
		}
		
	}
	
}
