import java.util.*;
import java.io.*;

class Cloud{
	int x,y;
	Cloud(int x, int y){
		this.x = x;
		this.y = y;
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
	static ArrayList<Cloud> clouds = new ArrayList<>();
	static int[] dx = {0,-1,-1,-1,0,1,1,1};
	static int[] dy = {-1,-1,0,1,1,1,0,-1};
	static int N, M;
	static int[][] A;
	
	
	
	static void work(int d, int s) {
		d--;
		
		// clouds move
		boolean[][] wasCloud = new boolean[N][N];
		for(Cloud cloud : clouds) {
			int x = (cloud.x + dx[d]*s + N*100) % N;
			int y = (cloud.y + dy[d]*s + N*100) % N;
			wasCloud[x][y] = true;
			A[x][y]++;
		}
		
		// remove clouds
		clouds = new ArrayList<>();
		
		// water copying bug magic
		for(int i=0;i<N;i++) for(int j=0;j<N;j++) {
			
			if(wasCloud[i][j]) {
				// diagonal check
				for(int k=1;k<8;k+=2) {
					int x = i+dx[k], y = j+dy[k];
					if(x<0 || x>=N || y<0 || y>=N || A[x][y]==0) continue;
					A[i][j]++;
				}
			}
			
		}
		
		// new clouds generate
		for(int i=0;i<N;i++) for(int j=0;j<N;j++) {
			if(!wasCloud[i][j]) {
				if(A[i][j] >= 2) {
					clouds.add(new Cloud(i,j));
					A[i][j] -= 2;
				}				
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		// Input
		nextLine();
		N = nextInt();
		M = nextInt();
		A = new int[N][N];
		for(int i=0;i<N;i++) {
			nextLine();
			for(int j=0;j<N;j++) A[i][j] = nextInt();
		}
		
		// clouds initialize
		clouds.add(new Cloud(N-2,0));
		clouds.add(new Cloud(N-2,1));
		clouds.add(new Cloud(N-1,0));
		clouds.add(new Cloud(N-1,1));
		
		// Work
		while(M-- > 0) {
			nextLine();
			int d = nextInt(), s = nextInt();
			work(d,s);
		}
		
		// end
		int ans = 0;
		for(int i=0;i<N;i++) for(int j=0;j<N;j++) ans += A[i][j];
		bw.write(ans+"\n");
		
		bwEnd();
	}

}
