import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main16929 {
	
	static int N, M; // N = h, M = w
	static char[][] map;
	static int[] dh = {-1, 1, 0, 0};
	static int[] dw = {0, 0, -1, 1};
	static int start_h;
	static int start_w;
	static int cycle = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			String input = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = input.charAt(j);
			}
		}
		
		for (int i = 0; i< N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] >= 'A' && map[i][j] <= 'Z') {
					start_h = i;
					start_w = j;
					char color = map[i][j];
					checkCycle(color, i, j, cloneMap(), 0);
				}
				if (cycle != 0) {
					System.out.println("Yes");
					return ;
				}
					
			}
		}
		System.out.println("No");
	}

	static void checkCycle(char color, int h, int w, char[][] clone, int check) {
		if (check >= 4 && h == start_h && w == start_w) {
			cycle++;
			return ;
		}
				
		for (int i = 0; i < 4; i++) {
			int nh = h + dh[i];
			int nw = w + dw[i];
			if (nh >= 0 && nh < N && nw >= 0 && nw < M && clone[nh][nw] == color) {
				if (check == 1 && start_h == nh && start_w == nw)
					continue ;
				
				if (check != 0) {
					clone[h][w] = '0';
				}
				checkCycle(color, nh, nw, clone, check + 1);
				
			}
		}
	}
	
	static char[][] cloneMap() {
		char[][] clone = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				clone[i][j] = map[i][j];
			}
		}
		return clone;
	}
}

