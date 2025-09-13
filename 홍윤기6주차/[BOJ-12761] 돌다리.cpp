#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#define INF 987654321

using namespace std;

int n;
int A, B, N, M;
vector<int> arr;
vector<int> cache;

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> A >> B >> N >> M;
	arr = vector<int>(100001);
	cache = vector<int>(100001, INF);

	queue<pair<int, int>> q;
	q.push({ N, 0 });
	int r[] = { -1, 1, -A, A, -B, B };
	while (!q.empty()) {
		int position = q.front().first;
		int cnt = q.front().second;
		q.pop();

		if (position == M) break;
		for (int i = 0; i < 6; i++) {
			int nPosition = position + r[i];
			if (nPosition >= 0 && nPosition <= 100000 && cache[nPosition] > cnt+1) {
				q.push({ nPosition, cnt + 1 });
				cache[nPosition] = cnt + 1;
			}
		}
		if (position * A >= 0 && position * A <= 100000 && cache[position * A] > cnt + 1) {
			q.push({ position * A, cnt + 1 });
			cache[position * A] = cnt + 1;
		}
		if (position * B >= 0 && position * B <= 100000 && cache[position * B] > cnt + 1) {
			q.push({ position * B, cnt + 1 });
			cache[position * B] = cnt + 1;
		}
	}
	cout << cache[M] << endl;
	return 0;
}
