# CPP
```cpp
#include <iostream>
#include <tuple>
#include <algorithm>
using namespace std;

int main() {
	cin.tie(0)->sync_with_stdio(0);

	int N, M, D[201][201]{}, INF = 1e9 + 7;
	cin >> N >> M;
	tuple<int, int, int> E[20000]{};
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) if(i != j) D[i][j] = INF;
	for (int i = 0, a, b, c; i < M; i++) {
		cin >> a >> b >> c;
		D[b][a] = D[a][b] = min(D[a][b], c);
		E[i] = { a,b,c };
	}

	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) for (int k = 1; k <= N; k++) {
		int res = D[j][i] + D[i][k];
		D[j][k] = min(D[j][k], D[j][i] + D[i][k]);
	}

	int ans = INF;
	for (int i = 1; i <= N; i++) {
		int res = 0;
		for (int j = 0; j < M; j++) {
			auto[s, e, l] = E[j];
			res = max(res, D[i][s] + l + D[i][e]);
		}
		ans = min(ans, res);
	}
	cout << ans / 2 << "." << ((ans & 1) ? "5" : "0");

}
```
