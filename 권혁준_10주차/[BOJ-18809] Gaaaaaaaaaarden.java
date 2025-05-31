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
	
	static int N, M, G, R;
	static int[][] O;
	static int canSpread = 0;
	static int[][] spreadPosition;
	
	static int ans = 0;
	static int green = 0, red = 0;
	
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		G = nextInt();
		R = nextInt();
		O = new int[N][M];
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) {
			O[i][j] = nextInt();
			canSpread += O[i][j] == 2 ? 1 : 0;
		}
		
		spreadPosition = new int[canSpread][2];
		int idx = 0;
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(O[i][j] == 2) {
			spreadPosition[idx][0] = i;
			spreadPosition[idx][1] = j;
			idx++;
		}
		
	}
	
	static void solve() throws Exception{

		chooseGreen(0,0);
		bw.write(ans + "\n");
		
	}
	
	static void chooseGreen(int cnt, int idx) {
		if(cnt == G) {
			chooseRed(0,0);
			return;
		}
		if(idx == canSpread) return;
		for(int i=idx;i<canSpread;i++) if((green & (1<<i)) == 0) {
			green ^= (1<<i);
			chooseGreen(cnt+1, i+1);
			green ^= (1<<i);
		}
	}
	
	static void chooseRed(int cnt, int idx) {
		if(cnt == R) {
			ans = Math.max(ans, simulation());
			return;
		}
		if(idx == canSpread) return;
		for(int i=idx;i<canSpread;i++) if(((green | red) & (1<<i)) == 0) {
			red ^= (1<<i);
			chooseRed(cnt+1, i+1);
			red ^= (1<<i);
		}
	}
	
	static int simulation() {
		
		int[][] A = new int[N][M];
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) A[i][j] = O[i][j] == 0 ? -1 : 0;
		
		boolean[][] block = new boolean[N][M];
		
		int res = 0;
		
		Queue<int[]> Q = new LinkedList<>();
		for(int i=0;i<canSpread;i++) {
			int x = spreadPosition[i][0];
			int y = spreadPosition[i][1];
			if((green & (1<<i)) != 0) {
				block[x][y] = true;
				Q.offer(new int[] {x,y,1});
				A[x][y] = 1;
			}
			if((red & (1<<i)) != 0) {
				block[x][y] = true;
				Q.offer(new int[] {x,y,2});
				A[x][y] = 2;
			}
		}
		
		while(true) {
			Queue<int[]> NQ = new LinkedList<>();
			while(!Q.isEmpty()) {
				int[] now = Q.poll();
				int x = now[0], y = now[1], p = now[2];
				for(int i=0;i<4;i++) {
					int xx = x+dx[i], yy = y+dy[i];
					if(xx<0 || xx>=N || yy<0 || yy>=M || block[xx][yy] || A[xx][yy] == -1) continue;
					NQ.offer(new int[] {xx,yy,p});
					A[xx][yy] |= p;
				}
			}
			
			while(!NQ.isEmpty()) {
				int[] now = NQ.poll();
				int x = now[0], y = now[1], p = now[2];
				if(block[x][y]) continue;
				block[x][y] = true;
				if(A[x][y] == 3) res++;
				else Q.offer(new int[] {x,y,p});
			}
			
			if(Q.isEmpty()) return res;
		}
		
	}
	
}
