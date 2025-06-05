package study12week;

import java.util.Scanner;

public class Main1405_미친로봇 {
	
	static int N, move[][] = {{0 ,-1}, {0, 1}, {1, 0}, {-1, 0}};//동, 서, 남, 북 (좌, 우, 하, 상)
	static double ewsn[] = new double[4], value;
	static boolean[][] visited;

	public static void main(String[] args) {
		init();
		visited[14][14] = true;
		dfs(14, 14, 0, 1);
		System.out.println(value);
	}
	
	static void dfs(int h, int w, int cnt, double val) {
		if (cnt == N) {
			value += val;
			return;
		}
		
		int nh, nw;
		for (int i = 0; i < 4; i++) {
			nh = h + move[i][0];
			nw = w + move[i][1];
			
			if (!visited[nh][nw]) {
				visited[nh][nw] = true;
				dfs(nh, nw, cnt + 1, val * ewsn[i]);
				visited[nh][nw] = false;
			}
		}
	}

	static void init() {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		for (int i = 0; i < 4; i++) {
			ewsn[i] = sc.nextInt() * 0.01;
		}
		visited = new boolean[30][30]; 
	}
}
