#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n, k;
vector<int> v;

void dfs(int num) {
	if (num == n) { // 경우의 수에 도달
		k--;
		if (k == 0) { // k번째 출력
			for (int i = 0; i < v.size(); i++) {
				cout << v[i];
				if (i != v.size() - 1)cout << '+';
			}
		}
		return;
	}
	else if (num > n) {
		return;
	}
	for (int i = 1; i <= 3; i++) {
		v.push_back(i);
		dfs(num + i);
		v.pop_back();
	}
}

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n >> k;
	dfs(0);
	if (k > 0) { // k번째에 도달하지 못 했다면,
		cout << "-1";
	}
	return 0;
}
