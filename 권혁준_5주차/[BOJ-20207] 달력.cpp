#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int N;
	cin >> N;
	vector<pair<int, int>> arr(N);
	for (auto &[s, e] : arr) cin >> s >> e;
	sort(arr.begin(), arr.end(), [](auto a, auto b) -> bool {return a.first == b.first ? a.second > b.second : a.first < b.first; });

	bool vis[1000]{};
	int h = 1, cnt[366]{}, c = N;
	while (c > 0) {
		int p = 0;
		for (int i = 0; i < N; i++) {
			if (vis[i]) continue;
			auto &[s, e] = arr[i];
			if (s <= p) continue;
			p = e;
			vis[i] = 1;
			c--;
			for (int j = s; j <= e; j++) cnt[j] = h;
		}
		h++;
	}

	int ans = 0;
	for (int i = 1; i <= 365; i++) {
		if (!cnt[i]) continue;
		int j = i, mx = 0;
		while (i <= 365 && cnt[i]) mx = max(mx, cnt[i++]);
		ans += mx * (i - j);
	}
	cout << ans;

}
