#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#define INF 987654321

using namespace std;

int n;
vector<int> v;

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    cin >> n;
	v = vector<int>(n+1);
	vector<int> dp(n+1, 1);
	for(int i = 1; i <= n; i++) cin >> v[i];
	dp[0] = 1;
	int ans = 1;
	for(int i = 1; i <= n; i++) {
		for(int j = 1; j < i; j++) {
			if(v[i] > v[j]) {
				dp[i] = max(dp[j]+1, dp[i]);
				ans = max(ans, dp[i]);
			}
		}
	}
	cout << n - ans;
	

	return 0;
}
