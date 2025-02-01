import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main14716 {
	
	// M = H, N = W
	static int M, N;
	static int[][] banner;
	static int[] dh = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
	static int[] dw = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
	
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException{
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		banner = new int[M + 1][N + 1];
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				banner[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int word_num = 0;
		for (int i = 1; i <= M; i++) {
			for (int j = 1; j <= N; j++) {
				if (banner[i][j] == 1) { // 전체를 돌면서 1을 만나면 상,하, 좌, 우, 대각선을 체크하면서 모두 0으로 만들어준다.
					parseWord(i, j);
					word_num++;
				}
			}
		}
		System.out.println(word_num);
	}
	
	static void parseWord(int h, int w) {
		banner[h][w] = 0;
		for (int i = 0; i < 9; i++) {
			int mh = h + dh[i];
			int mw = w + dw[i];
			
			if (mh > 0 && mw > 0 && mh <= M && mw <= N && banner[mh][mw] == 1) {
				parseWord(mh, mw);
			}
		}
	}
}
