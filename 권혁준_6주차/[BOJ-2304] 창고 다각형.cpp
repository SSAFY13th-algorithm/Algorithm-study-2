#include <iostream>
#include <algorithm>
using namespace std;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int N, Y[1001]{}, Z[1001]{};
	cin >> N;
	for (int i = 0, a, b; i < N; i++) {
		cin >> a >> b;
		Y[a] = b;
	}

	for (int i = 1; i <= 1000; i++) Z[i] = max(Z[i - 1], Y[i]);

	int ans = 0, from_right = 0;
	for (int i = 1000; i >= 1; i--) {
		from_right = max(from_right, Y[i]);
		Z[i] = min(Z[i], from_right);
		ans += Z[i];
	}
	cout << ans;

}
