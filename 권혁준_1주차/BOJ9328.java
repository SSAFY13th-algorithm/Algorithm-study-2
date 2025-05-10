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
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	static void newLine() throws Exception { st = new StringTokenizer(br.readLine()); }
	
	public static void main(String[] args) throws Exception {
		
		int T = Integer.parseInt(br.readLine());
		while(T-- > 0) {
			
			newLine();
			int N = nextInt(), M = nextInt();
			
			// map initialize
			char[][] arr = new char[N+2][M+2];
			for(int i=0;i<N+2;i++) {
				arr[i][0] = '.';
				arr[i][M+1] = '.';
			}
			for(int j=0;j<M+2;j++) {
				arr[0][j] = '.';
				arr[N+1][j] = '.';
			}
			for(int i=1;i<=N;i++) {
				String line = br.readLine();
				for(int j=1;j<=M;j++) arr[i][j] = line.charAt(j-1);
			}
			
			// key initialize
			boolean[] key = new boolean[26];
			String line = br.readLine();
			if(!line.equals("0")) for(int i=0;i<line.length();i++) {
				key[line.charAt(i)-'a'] = true;
			}
			
			// moving direction initialize
			int[] dx = {-1,0,0,1};
			int[] dy = {0,-1,1,0};
			
			// work
			int ans = 0;
			
			while(true) {
				
				int doors = 0;
				boolean change = false;
				
				boolean[][] vis = new boolean[N+2][M+2];
				Queue<Node> Q = new LinkedList<>();
				Q.offer(new Node(0,0));
				vis[0][0] = true;
				while(!Q.isEmpty()) {
					Node now = Q.poll();
					if(arr[now.x][now.y] == '$') {
						ans++;
						arr[now.x][now.y] = '.'; 
					}
					if('a' <= arr[now.x][now.y] && arr[now.x][now.y] <= 'z') {
						change = true;
						key[arr[now.x][now.y]-'a'] = true;
						arr[now.x][now.y] = '.'; 
					}
					if('A' <= arr[now.x][now.y] && arr[now.x][now.y] <= 'Z') {
						change = true;
						arr[now.x][now.y] = '.'; 
						doors++;
					}
					for(int i=0;i<4;i++) {
						int xx = now.x+dx[i], yy = now.y+dy[i];
						if(xx<0 || xx>N+1 || yy<0 || yy>M+1 || vis[xx][yy] || arr[xx][yy] == '*') continue;
						if('A' <= arr[xx][yy] && arr[xx][yy] <= 'Z' && !key[arr[xx][yy]-'A']) continue;
						Q.offer(new Node(xx,yy));
						vis[xx][yy] = true;
					}
				}

				if(!change) break;
			}
			bw.write(ans+"\n");
			
		}
		
		bw.close();
	}

}
