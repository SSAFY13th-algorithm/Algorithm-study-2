import java.util.*;
import java.io.*;

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
	
	static int[][][][] A;
	static int[] order;
	static int[] rotating;
	
	static final int INF = (int)1e9;
	static int ans = INF;
	static int[] dx = {1,-1,0,0,0,0};
	static int[] dy = {0,0,1,-1,0,0};
	static int[] dz = {0,0,0,0,1,-1};
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		A = new int[5][4][5][5];
		for(int n=0;n<5;n++) {
			for(int x=0;x<5;x++) for(int y=0;y<5;y++) A[n][0][x][y] = nextInt();
		}
		
	}
	
	static void solve() throws Exception{
		
		////////////// rotate
		
		for(int n=0;n<5;n++) {
			for(int r=0;r<3;r++) {
				for(int x=0;x<5;x++) for(int y=0;y<5;y++) A[n][r+1][y][4-x] = A[n][r][x][y];
			}
		}
		
		////////////// bruteforcing
		
		order = new int[5];
		permutation(0,0);
		
		bw.write((ans == INF ? -1 : ans) + "\n");

	}
	
	static void permutation(int cnt, int state) {
		if(cnt == 5) {
			for(int i=0;i<1024;i++) {
				int temp = i;
				rotating = new int[5];
				for(int j=0;j<5;j++) {
					rotating[j] |= (temp & 1);
					temp >>= 1;
					rotating[j] <<= 1;
					rotating[j] |= (temp & 1);
					temp >>= 1;
				}
				
				int[][][] B = new int[5][5][5];
				for(int z=0;z<5;z++) for(int x=0;x<5;x++) for(int y=0;y<5;y++) {
					B[x][y][z] = A[order[z]][rotating[order[z]]][x][y];
				}
				
				ans = Math.min(bfs(B), ans);
			}
			return;
		}
		for(int i=0;i<5;i++) {
			if((state & (1<<i)) == 0){
				order[cnt] = i;
				permutation(cnt+1, state | (1<<i));
				order[cnt] = -1;
			}
		}
	}
	
	static int bfs(int[][][] B) {
		
		Queue<int[]> Q = new LinkedList<>();
		boolean[][][] vis = new boolean[5][5][5];
		if(B[0][0][0] == 0) return INF;
		vis[0][0][0] = true;
		Q.offer(new int[] {0,0,0,0});
		
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			int x = now[0], y = now[1], z = now[2], t = now[3];
			if(x == 4 && y == 4 && z == 4) return t;
			for(int i=0;i<6;i++) {
				int xx = x+dx[i], yy = y+dy[i], zz = z+dz[i];
				if(xx<0 || xx>=5 || yy<0 || yy>=5 || zz<0 || zz>=5 || vis[xx][yy][zz]) continue;
				if(B[xx][yy][zz] == 0) continue;
				vis[xx][yy][zz] = true;
				Q.offer(new int[] {xx,yy,zz,t+1});
			}
		}
		
		return INF;
	}
	
}
