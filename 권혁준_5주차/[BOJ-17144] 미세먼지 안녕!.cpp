#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int dx[4] = { 1,0,-1,0 };
int dy[4] = { 0,1,0,-1 };
int R, C, T, A[50][50]{}, K = -1;

void spread() {
	int NA[50][50]{};
	for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) {
		if (A[i][j] <= 0) {
			if (A[i][j] == -1) NA[i][j] = A[i][j];
			continue;
		}
		int cnt = 0, val = A[i][j] / 5;
		for (int k = 0; k < 4; k++) {
			int x = i + dx[k], y = j + dy[k];
			if (x < 0 || x >= R || y < 0 || y >= C || A[x][y] == -1) continue;
			cnt++;
			NA[x][y] += val;
		}
		NA[i][j] += A[i][j] - cnt * val;
	}
	for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) A[i][j] = NA[i][j];
}

void cycle() {
	int x = 0, y = 0, temp = A[x][y], dir = 1;
	while (x != 1 || y != 0) {
		int nx = x + dx[dir], ny = y + dy[dir];
		if (nx < 0 || nx > K || ny < 0 || ny >= C) {
			dir = (dir + 3) % 4;
			nx = x + dx[dir], ny = y + dy[dir];
		}
		if (A[x][y] != -1) A[x][y] = max(0, A[nx][ny]);
		x = nx, y = ny;
	}
	A[x][y] = temp;

	x = R - 1, y = 0, temp = A[x][y], dir = 1;
	while (x != R - 2 || y != 0) {
		int nx = x + dx[dir], ny = y + dy[dir];
		if (nx <= K || nx >= R || ny < 0 || ny >= C) {
			dir = (dir + 1) % 4;
			nx = x + dx[dir], ny = y + dy[dir];
		}
		if (A[x][y] != -1) A[x][y] = max(0, A[nx][ny]);
		x = nx, y = ny;
	}
	A[x][y] = temp;
}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> R >> C >> T;
	for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) {
		cin >> A[i][j];
		if (A[i][j] == -1 && K == -1) K = i;
	}

	while (T-- > 0) {
		spread();
		cycle();
		
	}

	int ans = 0;
	for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) ans += max(A[i][j], 0);
	cout << ans;

}
