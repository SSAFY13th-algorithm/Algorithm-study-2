#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#include <sstream>
#define INF 987654321
#define lld long long

#define MAX_N 200
#define MAX_K 1000
#define pipii pair<int, pair<int, int>>

using namespace std;

int n, k;
int arr[MAX_N][MAX_N];
//bool visited[MAX_N][MAX_N];
int r[4][2] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

bool checkRange(int y, int x) {
	return y >= 0 && x >= 0 && y < n && x < n;
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);

	cin >> n >> k;

	priority_queue <pipii, vector<pipii>, greater<pipii>> pq;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> arr[i][j];
			if (arr[i][j] != 0) {
				pq.push({ arr[i][j], {i, j} });
			}
		}
	}
	int S, X, Y;
	cin >> S >> X >> Y;

	for (int s = 0; s < S; s++) {
		vector<pipii> v;
		while (!pq.empty()) {
			pipii p = pq.top();
			pq.pop();
			int n = p.first;
			int y = p.second.first;
			int x = p.second.second;
			for (int i = 0; i < 4; i++) {
				int ny = y + r[i][0];
				int nx = x + r[i][1];
				if (!checkRange(ny, nx)) continue;
				if (arr[ny][nx] == 0) {
					arr[ny][nx] = n;
					v.push_back({ n, {ny, nx} });
				}
			}
		}
		for (int i = 0; i < v.size(); i++) pq.push(v[i]);
	}
	cout << arr[X-1][Y-1] << endl;
	return 0;
}
