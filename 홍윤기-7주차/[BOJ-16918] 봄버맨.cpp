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

int R, C, N;
int arr[201][201];
int r[4][2] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

void setBomb() {
	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			if (arr[i][j] == 0) arr[i][j] = 4;
		}
	}
}

void next() {
	vector<pair<int, int>> b;
	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			if (arr[i][j] > 0) {
				arr[i][j]--;
				if (arr[i][j] == 0) {
					b.push_back({ i, j });
				}
			}
		}
	}
	for (int i = 0; i < b.size(); i++) {
		for (int k = 0; k < 4; k++) {
			int ny = b[i].first + r[k][0];
			int nx = b[i].second + r[k][1];
			if (ny < 0 || nx < 0 || ny >= R || nx >= C) continue;
			arr[ny][nx] = 0;
		}
	}
}

void printFeild() {
	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			cout << (arr[i][j] == 0 ? '.' : 'O');
			//cout << arr[i][j];
		}cout << endl;
	}
}

void solve() {
	if (N > 4) {
		N = (N-1+4) % 4+5;
	}
	// 1 2 3 4 5 6 7 8 9 10
	for (int i = 0; i < N-1; i++) {
		if (i % 2 == 0) setBomb();
		next();
	}
	printFeild();
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> R >> C >> N;
	for (int i = 0; i < R; i++) {
		string s;
		cin >> s;
		for (int j = 0; j < C; j++) {
			arr[i][j] = (s[j] == '.' ? 0 : 2);
		}
	}
	solve();

	return 0;
}
