import java.util.*;
import java.io.*;

class Node{
	int x,y;
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	
	static boolean ans = false;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	static int N, M;
	static char[][] arr;
	static boolean[][] vis;
	
	static void dfs(Node now, Node prev) {
		for(int i=0;i<4;i++) {
			int x = now.x + dx[i], y = now.y + dy[i];
			if(x<0 || x>=N || y<0 || y>=M || arr[x][y]!=arr[now.x][now.y]) continue;
			if(!vis[x][y]) {
				vis[x][y] = true;
				dfs(new Node(x,y), now);
				continue;
			}
			if(x==prev.x && y==prev.y) continue;
			ans = true;
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		nextLine();
		N = nextInt();
		M = nextInt();
		arr = new char[N][M];
		vis = new boolean[N][M];
		
		for(int i=0;i<N;i++) {
			String line = br.readLine();
			for(int j=0;j<M;j++) arr[i][j] = line.charAt(j);
		}
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(!vis[i][j]) {
			char base = arr[i][j];
			vis[i][j] = true;
			dfs(new Node(i,j), new Node(-1,-1));
		}
		bw.write(ans ? "Yes" : "No");
		
		bw.flush();
		bw.close();
	}

}
