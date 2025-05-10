#include <iostream>
#include <queue>
#include <tuple>
using namespace std;

int dx[4] = { 1,0,-1,0 };
int dy[4] = { 0,1,0,-1 };
int N, M, S[1001][1001]{}, H, W, sx, sy, ex, ey;
bool vis[1001][1001]{};

bool wall(int x, int y) { return S[x][y] - S[x - H][y] - S[x][y - W] + S[x - H][y - W] > 0; }

bool can(int x, int y) {
	return 1 <= x - H + 1 && x <= N && 1 <= y - W + 1 && y <= M && !wall(x, y);
}

int bfs(int p, int q) {
	queue<tuple<int, int, int>> Q;
	Q.emplace(p, q, 0);
	vis[p][q] = true;
	while (!Q.empty()) {
		auto [x, y, t] = Q.front();
		Q.pop();
		if (x == ex && y == ey) return t;
		for (int i = 0; i < 4; i++) {
			int xx = x + dx[i], yy = y + dy[i];
			if (can(xx, yy) && !vis[xx][yy]) {
				vis[xx][yy] = true;
				Q.emplace(xx, yy, t + 1);
			}
		}
	}

	return -1;
}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> N >> M;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= M; j++) {
		cin >> S[i][j];
		S[i][j] += S[i - 1][j] + S[i][j - 1] - S[i - 1][j - 1];
	}
	cin >> H >> W >> sx >> sy >> ex >> ey;
	sx += H - 1, sy += W - 1;
	ex += H - 1, ey += W - 1;

	cout << bfs(sx, sy);
	
}
