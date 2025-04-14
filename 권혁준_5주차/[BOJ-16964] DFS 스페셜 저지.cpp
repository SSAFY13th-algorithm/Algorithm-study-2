#include <iostream>
#include <vector>
#include <set>
using namespace std;

set<int> S[100001]{};
int par[100001]{}, vis[100001]{}, cnt[100001]{};
int N;

void dfs(int n, int p) {
	par[n] = p;
	for (int i : S[n]) {
		if (i == p) continue;
		dfs(i, n);
	}
}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> N;
	for (int i = 1, a, b; i < N; i++, S[a].insert(b), S[b].insert(a)) cin >> a >> b;
	dfs(1, 0);

	int r;
	cin >> r;
	if (r != 1) return cout << 0, 0;
	vis[r] = 1;
	for (int i = 2, a; i <= N; i++) {
		cin >> a;
		if (vis[a]) return cout << 0, 0;
		if (S[r].count(a)) {
			cnt[r]++;
			vis[a]++;
		}
		else {
			while (r != 0) {
				if (cnt[r] == S[r].size() - 1) r = par[r];
				else return cout << 0, 0;
				if (S[r].count(a)) {
					cnt[r]++;
					vis[a]++;
					break;
				}
			}
		}
		r = a;
	}
	cout << 1;

}
