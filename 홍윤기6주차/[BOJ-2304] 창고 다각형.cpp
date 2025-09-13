#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#define INF 987654321
#define lld long long

using namespace std;

int n;
vector<int> v;

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);

	cin >> n;
	v = vector<int>(1001);
	int index = 0, max_height = 0;
	for(int i = 0; i < n; i++) {
		int a, b; cin >> a >> b;
		v[a] = b;
		if(b > max_height) {
			max_height = b;
			index = a;
		}
	}
	// sort(v.begin(), v.end());
	int ans = max_height;
	int height = 0;
	for(int i = 0; i < index; i++) {
		height = max(height, v[i]);
		ans += height;
	}
	height = 0;
	for(int i = 1000; i > index; i--) {
		height = max(height, v[i]);
		ans += height;
	}

	cout << ans << endl;

	return 0;
}
