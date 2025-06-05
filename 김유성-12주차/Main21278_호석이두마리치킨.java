import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M, dist[][], INF = 1_000_000_000;

	public static void main(String[] args) throws IOException {
		init();
		solve();
	}
	
	static void solve() {
		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				if (i == k) continue;
				for (int j = 1; j <= N; j++) {
					if (i == j || j == k) continue;
					
					dist[i][j] = Math.min(dist[i][k] + dist[k][j], dist[i][j]);
				}
			}
		}
		int min = Integer.MAX_VALUE;
		StringBuilder sb = new StringBuilder("");
		for (int i = 1; i < N; i++) {
			for (int j = i + 1; j <= N; j++) {
				int ret = 0;
				
				for (int k = 1; k <= N; k++) {
					if (i == k || j == k) continue;
					
					if (dist[i][k] <= dist[j][k]) {
						ret += dist[i][k];
					} else {
						ret += dist[j][k];
					}
				}
				if (min > ret * 2) {
					min = ret * 2;
					sb = new StringBuilder(i + " " + j + " " + min);
				}
			}
		}
		System.out.println(sb.toString());
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		dist = new int[N + 1][N + 1];
		for (int i = 0; i <= N; i++) {
			Arrays.fill(dist[i], INF);
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			dist[a][b] = 1;
			dist[b][a] = 1;
		}
	}
}
