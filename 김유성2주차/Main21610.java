import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main21610 {
	
	static int N, M;
	static int[][] map;
	static int[][] increse_map;
	
	static int[][] move_cloud;
	static int[][] cloud;
	
	
	static int[] dh = {-100, 0, -1, -1, -1, 0, 1, 1, 1}; //←, ↖, ↑, ↗, →, ↘, ↓, ↙
	static int[] dw = {-100, -1, -1, 0, 1, 1, 1, 0, -1};
	
	static int[] waterh = {-1, -1, 1, 1};
	static int[] waterw = {-1, 1, -1, 1};
			
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int [N + 1][N + 1];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cloud = new int [N + 1][N + 1];
		move_cloud = new int [N + 1][N + 1];
		cloud[N][1] = 1;
		cloud[N][2] = 1;
		cloud[N -1][1] = 1;
		cloud[N -1][2] = 1;
		
		// 처음 비바라기 시전.
		st = new StringTokenizer(br.readLine());
		int di = Integer.parseInt(st.nextToken());
		int si = Integer.parseInt(st.nextToken());
		moveCloud(di, si);
		increse_map = new int [N + 1][N + 1];
		waterMagic();
		
		for (int i = 2; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			di = Integer.parseInt(st.nextToken());
			si = Integer.parseInt(st.nextToken());
			
			// 새로 이동한 구름을 저장할 배열을 할당
			move_cloud = new int[N + 1][N + 1];
			//구름을 이동
			moveCloud(di, si);
			increse_map = new int [N + 1][N + 1];
			//물복사 버그마법 시전
			waterMagic();
		}
		long ret = sumWater();
		System.out.println(ret);
	}
	
	static void moveCloud(int di, int si) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (cloud[i][j] == 1) {
					int mh = moveH(di, si, i); 
					int mw = moveW(di, si, j);
					move_cloud[mh][mw] = 1;
					map[mh][mw] += 1;
				}
			}
		}
	}
	
	
	static int moveH(int di, int si, int h) {
		for (int i = 0; i < si; i++) {
			int mh = h + dh[di];
			if (mh == 0) {
				h = N;
			} else if (mh == N + 1) {
				h = 1;
			} else {
				h = mh;
			}
		}
		return h;
	}
	
	static int moveW(int di, int si, int w) {
		for (int i = 0; i < si; i++) {
			int mw = w + dw[di];
			if (mw == 0) {
				w = N;
			} else if (mw == N + 1) {
				w = 1;
			} else {
				w = mw;
			}
		}
		return w;
	}
		
	static void waterMagic() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (move_cloud[i][j] == 1)
					waterIncrese(i, j);
			}
		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (increse_map[i][j] > 0)
					map[i][j] += increse_map[i][j];
			}
		}
		//새로 구름 생길곳을 다시 할당
		cloud = new int[N + 1][N + 1];
		// 구름을 생성
		makeCloud();
	}
	
	static void waterIncrese(int h, int w) {
		for (int i = 0; i < 4; i++) {
			int mh = h + waterh[i];
			int mw = w + waterw[i];
			if (mh >= 1 && mh <= N && mw >= 1 && mw <= N && map[mh][mw] > 0) {
				increse_map[h][w] += 1;
			}
		}
	}
	
	// move_cloud를 참고해서 cloud를 만든다. 이 때, move_cloud가 1이면 이전에 구름이 생긴 곳이다.
	static void makeCloud() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (move_cloud[i][j] == 0 && map[i][j] >= 2) {
					cloud[i][j] = 1;
					map[i][j] -= 2;
				}
			}
		}
	}
	
	static long sumWater() {
		long sum = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				sum += map[i][j];
			}
		}
		return sum;
	}
		
}

