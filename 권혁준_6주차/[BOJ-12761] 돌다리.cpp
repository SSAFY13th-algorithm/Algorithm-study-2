#include <iostream>
#include <queue>
#include <bitset>
using namespace std;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int A, B, N, M;
	cin >> A >> B >> N >> M;
	bitset<100001> v;
	queue<pair<int, int>> Q;
	Q.emplace(N, 0);
	v[N] = 1;

	int d[6] = { 1,-1,A,-A,B,-B };
	while (!Q.empty()) {
		auto[n, t] = Q.front();
		Q.pop();
		if (n == M) return cout << t, 0;
		for (int i = 0; i < 6; i++) {
			int x = n + d[i];
			if (x < 0 || x > 100000 || v[x]) continue;
			v[x] = 1;
			Q.emplace(x, t + 1);
		}
		if (n*A <= 100000 && !v[n*A]) { v[n*A] = 1; Q.emplace(n*A, t + 1); }
		if (n*B <= 100000 && !v[n*B]) { v[n*B] = 1; Q.emplace(n*B, t + 1); }
	}


}
