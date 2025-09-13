#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
using ll = long long;

int N;
vector<vector<int>> V(123457);
ll C[123457]{};

ll dfs(int n) {
	ll res = C[n];
	for (int i : V[n]) res += dfs(i);
	return max(res, 0LL);
}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> N;
	for (int i = 2, a, b; i <= N; i++) {
		char o;
		cin >> o >> a >> b;
		V[b].push_back(i);
		C[i] = a * (o == 'S' ? 1 : -1);
	}

	cout << dfs(1);

}
