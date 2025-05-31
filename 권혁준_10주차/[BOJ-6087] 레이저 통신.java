import java.util.*;
import java.io.*;

class Edge{
	int s, e;
	double c;
	Edge(int s, int e, double c){
		this.s = s;
		this.e = e;
		this.c = c;
	}
}

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
	static char[][] A;
	static int sx = -1, sy, ex, ey;
	
	static final int INF = (int)1e9;
	
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		M = nextInt();
		N = nextInt();
		A = new char[N][];
		for(int i=0;i<N;i++) {
			A[i] = br.readLine().toCharArray();
			for(int j=0;j<M;j++) if(A[i][j] == 'C') {
				if(sx == -1) {
					sx = i;
					sy = j;
				}
				else {
					ex = i;
					ey = j;
				}
			}
		}
		
	}
	
	static void solve() throws Exception{

		Deque<int[]> D = new ArrayDeque<>();
		int[][][] cost = new int[N][M][4];
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) Arrays.fill(cost[i][j], INF);
		
		for(int i=0;i<4;i++) {
			cost[sx][sy][i] = 0;
			D.offerLast(new int[] {sx,sy,0,i});
		}
		
		while(!D.isEmpty()) {
			int[] now = D.pollFirst();
			int x = now[0], y = now[1], t = now[2], dir = now[3];
			if(t > cost[x][y][dir]) continue;
			if(x == ex && y == ey) {
				bw.write(t + "\n");
				return;
			}
			for(int i=0;i<4;i++) {
				int xx = x+dx[i], yy = y+dy[i];
				if(Math.abs(i-dir) == 2 || xx<0 || xx>=N || yy<0 || yy>=M || A[xx][yy] == '*') continue;
				
				if(i == dir) {
					if(cost[xx][yy][i] > t) {
						cost[xx][yy][i] = t;
						D.offerFirst(new int[] {xx,yy,t,i});
					}
				}
				else {
					if(cost[xx][yy][i] > t+1) {
						cost[xx][yy][i] = t+1;
						D.offerLast(new int[] {xx,yy,t+1,i});
					}
				}
			}
		}
		
	}
	
}
