#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#include <sstream>
#define INF 987654321
#define lld long long

using namespace std;

int N, K;
vector<lld> v;
vector<lld> sum;


int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> N >> K;
	v = vector<lld>(N);
	sum = vector<lld>(N + 1);
	for (int i = 0; i < N; i++) cin >> v[i];
	sort(v.begin(), v.end());
	for (int i = 1; i <= N; i++) sum[i] = v[i - 1] + sum[i-1];
	lld start = 1ll, end = v[v.size()-1] + K + 1;
	lld ans = 0;
	while (start <= end) {
		lld target = (start + end) / 2;
		vector<lld>::iterator iter = lower_bound(v.begin(), v.end(), target);
		int n = iter - v.begin();
		lld s = target*n - sum[n];
		if (s <= K) {
			ans = target;
			start = target + 1;
		}
		else {
			end = target - 1;
		}
	}
	cout << ans << endl;

	return 0;
}
