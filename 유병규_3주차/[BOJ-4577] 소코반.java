import java.io.*;
import java.util.*;

public class Main {
	private static char[][] map;
	private static int[] user;
	private static int[][] d = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	private static int r = 0, c = 0, goal = 0, success = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int game = 1;
		StringBuilder sb = new StringBuilder();
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());

			if (r == 0 && c == 0)
				break;

			map = new char[r][c];
			goal = 0;
			success = 0;
			for (int i = 0; i < r; i++) {
				String inputs = br.readLine();
				int index = 0;
				for (char ch : inputs.toCharArray()) {
					map[i][index] = ch;
					if (ch == 'w')
						user = new int[] { i, index };
					else if (ch == 'W') {
						user = new int[] { i, index };
						goal++;
					} else if (ch == '+')
						goal++;
					else if (ch == 'B') {
						goal++;
						success++;
					}
					index++;
				}
			}

			String operators = br.readLine();
			String result = "incomplete";
			// 이동 완료
			for (char operator : operators.toCharArray()) {
				if (goal == success) {
					result = "complete";
					break;
				}
				int[] move = getPoint(operator);

				int nx = user[0] + move[0];
				int ny = user[1] + move[1];
				if (nx < 0 || nx >= r || ny < 0 || ny >= c)
					continue;
				if (map[nx][ny] == '#')
					continue;

				move(nx, ny, move);
			}
			if (goal == success) {
				result = "complete";
			}
			//
			sb.append("Game " + game + ": " + result + "\n");
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					sb.append(map[i][j] + "");
				}
				sb.append("\n");
			}
			game++;
		}

		System.out.print(sb.toString().trim());
	}

	private static void move(int x, int y, int[] move) {
		if (map[x][y] == '.') {
			if (map[user[0]][user[1]] == 'w')
				map[user[0]][user[1]] = '.';
			else
				map[user[0]][user[1]] = '+';

			map[x][y] = 'w';
			user[0] = x;
			user[1] = y;
			return;
		}
		if (map[x][y] == '+') {
			if (map[user[0]][user[1]] == 'w')
				map[user[0]][user[1]] = '.';
			else
				map[user[0]][user[1]] = '+';

			map[x][y] = 'W';
			user[0] = x;
			user[1] = y;
			return;
		}
		int boxNx = x + move[0];
		int boxNy = y + move[1];
		if (boxNx < 0 || boxNx >= r || boxNy < 0 || boxNy >= c)
			return;
		if (map[boxNx][boxNy] == '#' || map[boxNx][boxNy] == 'b' || map[boxNx][boxNy] == 'B')
			return;

		if (map[boxNx][boxNy] == '.') {
			if (map[x][y] == 'b') {// b -> .
				if (map[user[0]][user[1]] == 'w')
					map[user[0]][user[1]] = '.';
				else
					map[user[0]][user[1]] = '+';
				map[x][y] = 'w';
				map[boxNx][boxNy] = 'b';
			} else {// B -> .
				if (map[user[0]][user[1]] == 'w')
					map[user[0]][user[1]] = '.';
				else
					map[user[0]][user[1]] = '+';
				map[x][y] = 'W';
				map[boxNx][boxNy] = 'b';
				success--;
			}
			user[0] = x;
			user[1] = y;
			return;
		}
		if (map[boxNx][boxNy] == '+') {
			if (map[x][y] == 'b') {// b -> +
				if (map[user[0]][user[1]] == 'w')
					map[user[0]][user[1]] = '.';
				else
					map[user[0]][user[1]] = '+';
				map[x][y] = 'w';
				map[boxNx][boxNy] = 'B';
				success++;
			} else {// B -> +
				if (map[user[0]][user[1]] == 'w')
					map[user[0]][user[1]] = '.';
				else
					map[user[0]][user[1]] = '+';
				map[x][y] = 'W';
				map[boxNx][boxNy] = 'B';
			}
			user[0] = x;
			user[1] = y;
			return;
		}
	}

	private static int[] getPoint(char operator) {
		int index = 0;
		switch (operator) {
		case 'U':
			index = 0;
			break;
		case 'R':
			index = 1;
			break;
		case 'D':
			index = 2;
			break;
		case 'L':
			index = 3;
			break;
		default:
			index = 0;
			break;
		}
		;
		return d[index];
	}

}
