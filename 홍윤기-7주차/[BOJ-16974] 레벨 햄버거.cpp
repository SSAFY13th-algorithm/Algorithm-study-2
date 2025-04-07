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

lld n, x;
lld arr[51] = { 0 };

lld solve(lld n, lld x) {
	if (n == 0) return x;
	if (x <= n) return 0;
	if (n == 1) return min(x-1, 3ll);
	if (arr[n]/2 >= x) return solve(n - 1, x-1);
	return arr[n-1]/2+1 + 1 + solve(n - 1, min(x - arr[n - 1] - 2, arr[n - 1]));
	return 0;
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n >> x;
	lld k = 1;
	arr[0] = 1;
	for (int i = 1; i <= n; i++) {
		arr[i] = k = 2 * k + 3;
	}
	cout << solve(n, x);

	return 0;
}
