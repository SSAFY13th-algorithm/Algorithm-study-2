import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}

public class Main {
	static StringTokenizer st;
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		st = new StringTokenizer(br.readLine());
		int N = nextInt(), M = nextInt();
		
		int[][] arr = new int[N][M];
		int[] dx = {-1,-1,-1,0,0,1,1,1};
		int[] dy = {-1,0,1,-1,1,-1,0,1};
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) arr[i][j] = nextInt();
		}
		
		boolean[][] vis = new boolean[N][M];
		int ans = 0;
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(!vis[i][j] && arr[i][j]==1) {
			Queue<Node> Q = new LinkedList<>();
			Q.offer(new Node(i,j));
			vis[i][j] = true;
			while(!Q.isEmpty()) {
				Node n = Q.poll();
				for(int k=0;k<8;k++) {
					int xx = n.x+dx[k], yy = n.y+dy[k];
					if(xx<0 || xx>=N || yy<0 || yy>=M || vis[xx][yy] || arr[xx][yy]==0) continue;
					Q.offer(new Node(xx,yy));
					vis[xx][yy] = true;
				}
			}
			ans++;
		}
		bw.write(ans+"\n");
		
		bw.close();
	}

}
