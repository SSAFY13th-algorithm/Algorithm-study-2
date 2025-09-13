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

	static int N, K;
	static int[][] A;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	static List<int[]>[] P;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		K = nextInt();
		P = new List[K+1];
		for(int i=1;i<=K;i++) P[i] = new ArrayList<>();
		
		A = new int[N][N];
		for(int i=0;i<N;i++) {
			nextLine();
			for(int j=0;j<N;j++) {
				int a = nextInt();
				A[i][j] = a;
				if(a != 0) P[a].add(new int[] {i,j});
			}
		}
		
	}
	
	static void solve() throws Exception{
		
		nextLine();
		bw.write(bfs(nextInt(), nextInt(), nextInt()) + "\n");
		
	}
	
	static int bfs(int s, int px, int py) {
		
		Queue<int[]> Q = new LinkedList<>();
		for(int i=1;i<=K;i++) for(int[] p : P[i]) Q.offer(new int[] {p[0],p[1],i,0});
		
		while(!Q.isEmpty()) {
			
			int[] now = Q.poll();
			int x = now[0], y = now[1], v = now[2], t = now[3];
			if(t >= s) break;
			for(int i=0;i<4;i++) {
				int xx = x+dx[i], yy = y+dy[i];
				if(xx<0 || xx>=N || yy<0 || yy>=N || A[xx][yy] != 0) continue;
				A[xx][yy] = v;
				Q.offer(new int[] {xx,yy,v,t+1});
			}
			
		}
		return A[px-1][py-1];
		
	}
	
}
