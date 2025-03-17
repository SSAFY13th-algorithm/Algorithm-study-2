#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#include <sstream>
#define INF 987654321

using namespace std;

int n;
string s;
vector<int> dp;

char getNext(char c) {
	if (c == 'B') return'O';
	if (c == 'O')return 'J';
	return 'B';
}

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n;
	cin >> s;
	queue<pair<int, pair<char, int>>> q;
	dp = vector<int>(n + 1, INF);
	dp[0] = 0;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < i; j++) {
			if (getNext(s[j]) == s[i]) {
				dp[i] = min(dp[i], (j-i)*(j-i) + dp[j]);
			}
		}
	}
	cout << (dp[n - 1] == INF ? -1 : dp[n - 1]);
	return 0;
}
