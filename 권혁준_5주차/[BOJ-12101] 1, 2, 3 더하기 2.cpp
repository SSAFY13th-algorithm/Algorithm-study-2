#include <iostream>
#include <string>
#include <algorithm>
using namespace std;

int N, K, cnt = 0;
string ans = "-1";

void bck(int sum, string s) {
	if (sum == N) {
		if (++cnt == K) ans = s;
		return;
	}
	for (int i = 1; i <= min(3, N - sum); i++) bck(sum + i, s + "+" + to_string(i));
}

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	cin >> N >> K;
	for (int i = 1; i <= min(N,3); i++) bck(i, to_string(i));
	cout << ans;

}
