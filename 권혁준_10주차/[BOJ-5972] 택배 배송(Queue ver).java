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

		int[] R = new int[N+1];
		Arrays.fill(R, INF);
		Deque<Integer>[] D = new ArrayDeque[1001];
		for(int i=0;i<=1000;i++) D[i] = new ArrayDeque<>();
		
    R[1] = 0;
		int cycle = 0, cur = 0;
		D[0].offerLast(1);
		while(true) {
			while(!D[cur].isEmpty()) {
				int now = D[cur].pollFirst();
				if(cycle*1001 + cur > R[now]) continue;
				if(now == N) {
					bw.write((cycle*1001 + cur) + "\n");
					return;
				}
				for(int[] e:V[now]) {
					int next = e[0], cost = e[1];
					if(R[next] > cycle*1001 + cur + cost) {
						R[next] = cycle*1001 + cur + cost;
						D[(cur+cost)%1001].offerLast(next);
					}
				}
			}
      if(cur == 1000) cycle++;
			cur = (cur+1)%1001;
		}
		
	}
	
}
