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
	static char[][] A;
	static int sx = -1, sy, ex, ey;
	
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = Integer.parseInt(br.readLine());
		A = new char[N][N];
		for(int i=0;i<N;i++) {
			A[i] = br.readLine().toCharArray();
			for(int j=0;j<N;j++) if(A[i][j] == '#') {
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
		
		bw.write(bfs(sx,sy) + "\n");
		
	}
	
	static int bfs(int px, int py) {
		
		Deque<int[]> D = new ArrayDeque<>();
		boolean[][][][] vis = new boolean[N][N][4][3];
		for(int i=0;i<4;i++) {
			int x = px+dx[i], y = py+dy[i];
			if(safe(x,y)) {
				if(!vis[x][y][i][0]) {
					vis[x][y][i][0] = true;
					D.offerFirst(new int[] {x,y,i,0,0});
				}
				if(A[x][y] == '!') {
					for(int k=1;k<=2;k++) {
						if(!vis[x][y][nextDir(i,k)][k]) {
							vis[x][y][nextDir(i,k)][k] = true;
							D.offerLast(new int[] {x,y,nextDir(i,k),k,1});
						}
					}
					
				}
			}
		}
		
		int time = 0;
		
		while(!D.isEmpty()) {
			int[] now = D.pollFirst();
			int x = now[0], y = now[1], dir = now[2], rad = now[3];
			time = now[4];
			if(x == ex && y == ey) break;
			
			int xx = x+dx[dir], yy = y+dy[dir];
			if(!safe(xx,yy)) continue;
			
			if(!vis[xx][yy][dir][0]) {
				vis[xx][yy][dir][0] = true;
				D.offerFirst(new int[] {xx,yy,dir,0,time});
			}
			if(A[xx][yy] == '!') {
				for(int k=1;k<=2;k++) {
					if(!vis[xx][yy][nextDir(dir,k)][k]) {
						vis[xx][yy][nextDir(dir,k)][k] = true;
						D.offerLast(new int[] {xx,yy,nextDir(dir,k),k,time+1});
					}
				}
			}
			
		}
		return time;
		
	}
	
	static boolean safe(int x, int y) {
		return 0<=x&&x<N && 0<=y&&y<N && A[x][y] != '*';
	}
	
	static int nextDir(int dir, int k) {
		if(k == 1) {
			if(dir == 0) dir = 3;
			else if(dir == 1) dir = 2;
			else if(dir == 2) dir = 1;
			else dir = 0;
		}
		else {
			if(dir == 0) dir = 1;
			else if(dir == 1) dir = 0;
			else if(dir == 2) dir = 3;
			else dir = 2;
		}
		return dir;
	}
	
}
