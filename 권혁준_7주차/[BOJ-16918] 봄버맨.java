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
	static int R, C, N;
	static int[][] A;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		R = nextInt();
		C = nextInt();
		N = nextInt();
		A = new int[R][C];
		for(int i=0;i<R;i++) {
			char[] temp = br.readLine().toCharArray();
			for(int j=0;j<C;j++) A[i][j] = temp[j] == '.' ? 0 : 3;
		}
		
	}
	
	static void solve() throws Exception{
		
		for(int k=1;k<=N;k++) {
			move(k);
		}
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) bw.write(A[i][j] == 0 ? '.' : 'O');
			bw.write("\n");
		}
		
	}
	
	static void move(int bomb) {
		int[][] T = new int[R][C];
		for(int i=0;i<R;i++) for(int j=0;j<C;j++) {
			if(bomb%2 == 0) T[i][j] = A[i][j] == 0 ? 4 : A[i][j];
			else T[i][j] = A[i][j] == 0 ? 0 : A[i][j];
		}
		
		for(int i=0;i<R;i++) for(int j=0;j<C;j++) {
			if(A[i][j] == 1) {
				T[i][j] = 0;
				for(int k=0;k<4;k++) {
					int x = i+dx[k], y = j+dy[k];
					if(x<0 || x>=R || y<0 || y>=C) continue;
					T[x][y] = 0;
				}
			}
		}
		
		for(int i=0;i<R;i++) for(int j=0;j<C;j++) {
			if(T[i][j] > 0) A[i][j] = T[i][j] - 1;
			else A[i][j] = 0;
		}
		
	}
	
}
