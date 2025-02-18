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

	
	public static void main(String[] args) throws Exception {

		nextLine();
		int N = nextInt(), k = nextInt();
		boolean[][] wall = new boolean[N+2][2];

		for(int j=0;j<2;j++) {
			String line = br.readLine();
			for(int i=1;i<=N;i++) wall[i][j] = line.charAt(i-1)-'0' == 0 ? true : false;
		}
		
		boolean[][] vis = new boolean[N+2][2];
		vis[1][0] = true;
		Queue<int[]> Q = new LinkedList<>();
		Q.offer(new int[] {1,0,0});
		
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			if(now[2] >= now[0]) continue;
			if(now[0] > N) {
				bw.write("1");
				bwEnd();
				return;
			}
			else if(!vis[now[0]+1][now[1]] && !wall[now[0]+1][now[1]]) {
				vis[now[0]+1][now[1]] = true;
				Q.offer(new int[] {now[0]+1,now[1],now[2]+1});
			}
			
			if(now[0] > 0 && !vis[now[0]-1][now[1]] && !wall[now[0]-1][now[1]]) {
				vis[now[0]-1][now[1]] = true;
				Q.offer(new int[] {now[0]-1,now[1],now[2]+1});
			}
			
			if(now[0]+k > N) {
				bw.write("1");
				bwEnd();
				return;
			}
			else if(!vis[now[0]+k][now[1]^1] && !wall[now[0]+k][now[1]^1]) {
				vis[now[0]+k][now[1]^1] = true;
				Q.offer(new int[] {now[0]+k,now[1]^1,now[2]+1});
			}
		}
		
		bw.write("0");
		bwEnd();
	}

}
