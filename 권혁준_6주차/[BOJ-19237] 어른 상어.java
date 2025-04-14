import java.util.*;
import java.io.*;

class Shark{
	int id, dir, x, y;
	int[][] prior;
	Shark(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		prior = new int[5][5];
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
	static final int[] dx = {0,-1,1,0,0};
	static final int[] dy = {0,0,0,-1,1};
	
	static Shark[] sharks;
	static boolean[] alive;
	static int[][] A;
	static int[][] smell_id, smell_time;
	static int N, M, K, R;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		nextLine();
		N = nextInt();
		M = nextInt();
		K = nextInt();
		smell_id = new int[N][N];
		smell_time = new int[N][N];
		A = new int[N][N];
		sharks = new Shark[M+1];
		alive = new boolean[M+1];
		Arrays.fill(alive, true);
		R = M;
		
		
		for(int i=0;i<N;i++) {
			nextLine();
			for(int j=0;j<N;j++) {
				A[i][j] = nextInt();
				if(A[i][j] != 0) {
					Shark temp = new Shark(A[i][j], i, j);
					sharks[A[i][j]] = temp;
				}
			}
		}
		
		nextLine();
		for(int i=1;i<=M;i++) sharks[i].dir = nextInt();
		
		for(int i=1;i<=M;i++) {
			for(int k=1;k<=4;k++) {
				nextLine();
				for(int j=1;j<=4;j++) sharks[i].prior[k][j] = nextInt();
			}
		}
		
	}
	
	static void solve() throws Exception{

		for(int time=0;time<1000;time++) {
			SmellSpread();
			MoveSharks();
			SmellsTimeSpend();
			if(R == 1) {
				bw.write((time+1) + "\n");
				return;
			}
		}
		bw.write("-1");
		
	}
	
	static void SmellsTimeSpend() {
		
		for(int i=0;i<N;i++) for(int j=0;j<N;j++) {
			if(smell_time[i][j] != 0) smell_time[i][j]--;
			if(smell_time[i][j] == 0) smell_id[i][j] = 0;
		}
		
	}
	
	static void SmellSpread() {
		for(int i=1;i<=M;i++) if(alive[i]) {
			int x = sharks[i].x, y = sharks[i].y;
			smell_id[x][y] = sharks[i].id;
			smell_time[x][y] = K;
		}
	}
	
	static void MoveSharks() {
		
		int[][] NA = new int[N][N];
		for(int i=1;i<=M;i++) if(alive[i]) {
			int dir = sharks[i].dir;
			
			boolean done = false;
			for(int k=1;k<=4;k++) {
				int x = sharks[i].x + dx[sharks[i].prior[dir][k]];
				int y = sharks[i].y + dy[sharks[i].prior[dir][k]];
				if(x<0 || x>=N || y<0 || y>=N || smell_id[x][y] != 0) continue;
				done = true;
				if(NA[x][y] != 0) {
					R--;
					if(NA[x][y] < sharks[i].id) {
						alive[sharks[i].id] = false;
						break;
					}
					else alive[NA[x][y]] = false;
				}
				sharks[i].x = x;
				sharks[i].y = y;
				sharks[i].dir = sharks[i].prior[dir][k];
				NA[x][y] = sharks[i].id;
				break;
			}
			
			if(done) continue;
			for(int k=1;k<=4;k++) {
				int x = sharks[i].x + dx[sharks[i].prior[dir][k]];
				int y = sharks[i].y + dy[sharks[i].prior[dir][k]];
				if(x<0 || x>=N || y<0 || y>=N || smell_id[x][y] != sharks[i].id) continue;
				sharks[i].x = x;
				sharks[i].y = y;
				sharks[i].dir = sharks[i].prior[dir][k];
				NA[x][y] = sharks[i].id;
				break;
			}
		}
		A = NA;
		
		
	}
	
}
