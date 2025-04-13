import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static char[][] map = new char[3][3];
	static int X, O;
	static boolean win, visited[][] = new boolean[3][3];
	static int move[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
	static List<int[]> X_hw;
	static List<int[]> O_hw;

	public static void main(String[] args) throws IOException {
		while (init()) {
			if (solve()) {
				System.out.println("valid");
			} else {
				System.out.println("invalid");
			}
		}
	}

	static void dfs(int h, int w, int cnt, char c, int dir) {
		if (cnt == 3) {
			win = true;
			return;
		}

		int nh, nw;
		nh = h + move[dir][0];
		nw = w + move[dir][1];

		if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == c) {
			dfs(nh, nw, cnt + 1, c, dir);
		}
	}

	static boolean solve() {
		if (X - O == 0) { // O가 이김
			// o가 이겨야 되는데 x가 이기면 false
			for (int[] x : X_hw) {
				int nh, nw;
				for (int i = 0; i < 8; i++) {
					nh = x[0] + move[i][0];
					nw = x[1] + move[i][1];

					if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == 'X') {
						dfs(nh, nw, 2, 'X', i);
						if (win == true)
							return false;
					}
				}
			}

			// o가 이긴경우가 맞으면 true
			for (int[] o : O_hw) {
				int nh, nw;
				for (int i = 0; i < 8; i++) {
					nh = o[0] + move[i][0];
					nw = o[1] + move[i][1];

					if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == 'O') {
						dfs(nh, nw, 2, 'O', i);
						if (win == true)
							return true;
					}
				}
			}

		} else if (X - O == 1) { // X가 이김 or 게임 끝남
			if (X + O == 9) {
				for (int[] o : O_hw) {
					int nh, nw;
					for (int i = 0; i < 8; i++) {
						nh = o[0] + move[i][0];
						nw = o[1] + move[i][1];

						if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == 'O') {
							dfs(nh, nw, 2, 'O', i);
							if (win == true)
								return false;
						}
					}
				}
				return true;
			} else {
				for (int[] o : O_hw) {
					int nh, nw;
					for (int i = 0; i < 8; i++) {
						nh = o[0] + move[i][0];
						nw = o[1] + move[i][1];

						if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == 'O') {
							dfs(nh, nw, 2, 'O', i);
							if (win == true)
								return false;
						}
					}
				}

				for (int[] x : X_hw) {
					int nh, nw;
					for (int i = 0; i < 8; i++) {
						nh = x[0] + move[i][0];
						nw = x[1] + move[i][1];

						if (nh >= 0 && nw >= 0 && nh < 3 && nw < 3 && !visited[nh][nw] && map[nh][nw] == 'X') {
							dfs(nh, nw, 2, 'X', i);
							if (win == true)
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	static boolean init() throws IOException {
		String input = br.readLine().trim();
		if (input.equals("end"))
			return false;

		X_hw = new LinkedList<>();
		O_hw = new LinkedList<>();
		X = 0;
		O = 0;
		win = false;

		int len = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				map[i][j] = input.charAt(len++);
				if (map[i][j] == 'O') {
					O_hw.add(new int[] { i, j });
					O++;
				} else if (map[i][j] == 'X') {
					X_hw.add(new int[] { i, j });
					X++;
				}
			}

		return true;
	}
}

/*
 * 게임 조건 1. X먼저 시작하므로, X의 수가 1개 더 많거나 같아야 한다. => X의 수가 더 많으면 X가 이긴경우, 같으면 O가 이겨서
 * 중간에 끝난거.
 * 
 * 2. 빈칸이 있는 경우, 게임이 끝난 경우여야 한다. 3. 게임이 끝나야 하는데 더 진행되면 안된다. -> 해당 좌표의 말이 없을 때
 * 게임이 안끝나면 가능
 * 
 * 
 */
