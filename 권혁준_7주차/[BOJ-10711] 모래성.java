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

	static int H, W;
	static int[][] A;
	static boolean[][] vis;
	static int[] dx = {1,1,1,0,0,-1,-1,-1};
	static int[] dy = {1,0,-1,1,-1,1,0,-1};
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		H = nextInt();
		W = nextInt();
		A = new int[H][W];
		vis = new boolean[H][W];
		for(int i=0;i<H;i++) {
			char[] temp = br.readLine().toCharArray();
			for(int j=0;j<W;j++) {
				if(temp[j] == '.') continue;
				A[i][j] = temp[j]-'0';
			}
		}
		
	}
	
	static void solve() throws Exception{
		
		int ans = 0;
		
		Queue<int[]> Q = new LinkedList<>();
		for(int i=0;i<H;i++) for(int j=0;j<W;j++) if(A[i][j] == 0) {
			vis[i][j] = true;
			Q.add(new int[] {i,j,0});
		}
		
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			int x = now[0], y = now[1], time = now[2];
			ans = time;
			for(int k=0;k<8;k++) {
				int xx = x+dx[k], yy = y+dy[k];
				if(xx<0 || xx>=H || yy<0 || yy>=W || vis[xx][yy]) continue;
				if(--A[xx][yy] == 0) {
					vis[xx][yy] = true;
					Q.add(new int[] {xx,yy,time+1});
				}
			}
		}
		
		bw.write(ans + "\n");
		
	}
	
}
