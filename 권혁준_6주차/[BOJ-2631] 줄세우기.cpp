#include <iostream>
#include <algorithm>
using namespace std;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int N, arr[202]{}, dp[202]{}, ans = 0;
	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> arr[i];
		for (int j = 0; j < i; j++) if (arr[i] > arr[j]) dp[i] = max(dp[i], dp[j] + 1);
		ans = max(ans, dp[i]);
	}
	cout << N - ans;

}
