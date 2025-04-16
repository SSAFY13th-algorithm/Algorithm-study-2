package study13week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main23290_마법사상어와복제 {
	static class Fish {
		int h, w, dir;

		public Fish(int h, int w, int dir) {
			this.h = h;
			this.w = w;
			this.dir = dir;
		}

		public Fish(Fish f) {
			this.h = f.h;
			this.w = f.w;
			this.dir = f.dir;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	// 방향은 상, 좌, 하, 우로
	static int[] dh = { 0, -1, 0, 1, 0 }, dw = { 0, 0, -1, 0, 1 }, shark = new int[2];

//←, ↖, ↑, ↗, →, ↘, ↓, ↙ 
	static int move[][] = { { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 } };
	static int M, S, max, shark_move;

	static Queue<Fish> magic = new LinkedList<>();
	static List<Fish> fish = new LinkedList<>();
	
	static int[][] smell = new int[5][5];
	static boolean[][] visited = new boolean[5][5];

	static List<Fish> map[][];

	public static void main(String[] args) throws IOException {
		init();
		while (S-- > 0) {
			solve();
		}

		System.out.println(fish.size());
	}
  
	static void solve() {
		// 복제 마법 실행
		for (Fish f : fish) {
			magic.add(new Fish(f));
		}

		// 물고기 이동, 이동하면서 map에 채워주기
		moveFish();

		// 상어 이동 방향 구하기
		max = 0;
		shark_move = 555;
		visited[shark[0]][shark[1]] = true;
		moveShark(shark[0], shark[1], 0, 0, 0);
		visited[shark[0]][shark[1]] = false;

		// 구한 이동 방향대로 물고기를 잡아먹기. 이 때 물고기 냄새 남기기
		realMoveShark(shark_move);

		// map에 있는 물고기를 다시 list로
		fish.clear();
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				for (Fish f : map[i][j])
					fish.add(f);
			}
		}

		// 물고기의 냄새가 사라짐
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (smell[i][j] > 0)
					smell[i][j]--;
			}
		}
		
		// 복제마법 완료.
		while (!magic.isEmpty()) {
			Fish f = magic.poll();
			fish.add(f);
		}
	}

	static void realMoveShark(int dir) {
		int div = 100;
		while (dir != 0) {
			int d = dir / div;
			dir %= div;
			div /= 10;
			shark[0] += dh[d];
			shark[1] += dw[d];

			int h = shark[0];
			int w = shark[1];

			if (map[h][w].size() > 0) {
				smell[h][w] = 3;

				map[h][w].clear();
			}
		}
	}

	static void moveFish() {
		map = new LinkedList[5][5];

		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				map[i][j] = new LinkedList<>();
			}
		}

		for (Fish f : fish) {
			int nh, nw;

			To: for (int dir = 0; dir < 8; dir++) {
				int d = (f.dir - dir + 8) % 8;
				nh = f.h + move[d][0];
				nw = f.w + move[d][1];

				if (nh > 0 && nh <= 4 && nw > 0 && nw <= 4 && smell[nh][nw] == 0) {
					if (nh == shark[0] && nw == shark[1])
						continue;

					// 이동할 수 있으면 fish의 방향과 위치를 조정해 주고 dir반복문을 탈출
					f.h = nh;
					f.w = nw;
					f.dir = d;

					break To;
				}
			}
			map[f.h][f.w].add(f);
		}
	}

	static void moveShark(int h, int w, int cnt, int num, int dir) {
		if (cnt == 3) {
			if (max < num) {
				max = num;
				shark_move = dir;
			} else if (max == num && shark_move > dir) {
				shark_move = dir;
			}
			return;
		}

		int nh, nw;
		for (int i = 1; i <= 4; i++) {
			nh = h + dh[i];
			nw = w + dw[i];

			if (nh > 0 && nh <= 4 && nw > 0 && nw <= 4) {
				if (!visited[nh][nw]) {
					visited[nh][nw] = true;
					moveShark(nh, nw, cnt + 1, map[nh][nw].size() + num, dir * 10 + i);
					visited[nh][nw] = false;
				} else {
					moveShark(nh, nw, cnt + 1, num, dir * 10 + i);
				}
			}
		}
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken()) - 1;
			fish.add(new Fish(h, w, dir));
		}

		st = new StringTokenizer(br.readLine());
		shark[0] = Integer.parseInt(st.nextToken());
		shark[1] = Integer.parseInt(st.nextToken());
	}
}
