import java.util.*;
import java.io.*;

class Node{
	int x,y,t;
	Node(int x, int y, int t){
		this.x = x;
		this.y = y;
		this.t = t;
	}
}

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
	
	static int INF = 1234567890;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {

		nextLine();
		int R = nextInt(), C = nextInt();
		boolean[][] wall = new boolean[R][C];
		for(int i=0;i<R;i++) {
			String line = br.readLine();
			for(int j=0;j<C;j++) wall[i][j] = line.charAt(j)%2 == 1;
		}
		
		int[][][] time = new int[3][R][C];
		for(int k=0;k<3;k++) {
			// INF initialize
			for(int i=0;i<R;i++) Arrays.fill(time[k][i], INF);
			
			// BFS
			boolean[][] vis = new boolean[R][C];
			nextLine();
			int a = nextInt(), b = nextInt();
			a--;
			b--;
			Queue<Node> Q = new LinkedList<>();
			Q.offer(new Node(a,b,0));
			vis[a][b] = true;
			while(!Q.isEmpty()) {
				Node now = Q.poll();
				time[k][now.x][now.y] = now.t; 
				for(int i=0;i<4;i++) {
					int x = now.x+dx[i], y = now.y+dy[i], t = now.t+1;
					if(x<0 || x>=R || y<0 || y>=C || vis[x][y] || wall[x][y]) continue;
					vis[x][y] = true;
					Q.offer(new Node(x,y,t));
				}
			}
			
		}
		
		int ans = INF, cnt = 0;
		for(int i=0;i<R;i++) for(int j=0;j<C;j++) {
			int res = 0;
			for(int k=0;k<3;k++) res = Math.max(res, time[k][i][j]);
			if(res < ans) {
				ans = res;
				cnt = 1;
			}
			else if(res == ans) cnt++;
		}
		if(ans == INF) bw.write("-1");
		else bw.write(ans+"\n"+cnt);
		
		bwEnd();
	}

}
