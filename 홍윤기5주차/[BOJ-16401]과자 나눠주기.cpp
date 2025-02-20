#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#define INF 987654321

using namespace std;

int n, m;
int r;
vector<int> v;

// 이분탐색 -> log(10^9) ~~ 30
// 각 탐색에 걸리는 시간 -> 1,000,000(=10^6)
// 최대 연산 -> 30*1,000,000 = 30,000,000 => 3천만

bool shareSnack(int height, int nChild) {
	for (int i = 0; i < v.size() && nChild > 0; i++) {
		int k = v[i];
		if (k < height) break;
		while (k >= height && nChild > 0) {
			k -= height;
			nChild--;
		}
	}
	return nChild == 0;
}

int findLowerbound(int s, int e) {
	if (s >= e) return s;
	int mid = (s + e) / 2;
	bool result = shareSnack(mid, m);
	if (result) {
		return findLowerbound(mid + 1, e);
	}
	else {
		return findLowerbound(s, mid);
	}
}

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> m >> n;
	v = vector<int>(n);
	r = -1;
	for (int i = 0; i < n; i++) {
		cin >> v[i];
		r = max(r, v[i]);
	}
	sort(v.begin(), v.end(), greater<int>());
	cout << findLowerbound(1, r + 1)-1 << endl;
	return 0;
}
