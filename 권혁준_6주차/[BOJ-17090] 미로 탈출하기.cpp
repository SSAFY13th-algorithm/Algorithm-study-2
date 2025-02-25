#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
using ll = long long;

int N, M;
char A[500][500]{};
int vis[502][502]{};

int dfs(int x, int y) {
	if (x < 0 || x >= N || y < 0 || y >= M) return 1;
	int xx, yy;
	if (A[x][y] == 'U') xx = x - 1, yy = y;
	if (A[x][y] == 'D') xx = x + 1, yy = y;
	if (A[x][y] == 'L') xx = x, yy = y - 1;
	if (A[x][y] == 'R') xx = x, yy = y + 1;

	if (xx < 0 || xx >= N || yy < 0 || yy >= M) return vis[x][y] = 1;
	if (!vis[xx][yy]) {
		vis[xx][yy] = -1;
		return vis[x][y] = dfs(xx, yy);
	}
	return vis[x][y] = vis[xx][yy];

}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> N >> M;
	for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) cin >> A[i][j];

	for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) if (!vis[i][j]) {
		vis[i][j] = -1;
		vis[i][j] = dfs(i, j);
	}

	int ans = 0;
	for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) ans += vis[i][j] == 1;
	cout << ans;

}
