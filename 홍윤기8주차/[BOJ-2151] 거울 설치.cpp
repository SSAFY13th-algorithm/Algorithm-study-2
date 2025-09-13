#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#include <sstream>
#define INF 987654321
#define lld long long

#define MAX_N 1000000
#define MAX_M 700

using namespace std;

int n;
char arr[50][50];
vector<pair<int, int>> doors;
int r[4][2] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // UDLR

typedef struct Info {
	int y;
	int x;
	int n;
	int direction;
} Info;
class Compare {
public:
	bool operator()(const Info& a, const Info& b) {
		return a.n > b.n;
	}
};
bool checkRange(int y, int x) {
	return y >= 0 && x >= 0 && y < n && x < n;
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n;
	// y, x, d
	priority_queue<Info, vector<Info>, Compare> pq;
	for (int i = 0; i < n; i++) {
		string s;
		cin >> s;
		for (int j = 0; j < n; j++) {
			arr[i][j] = s[j];
			if (arr[i][j] == '#') doors.push_back({ i, j });
		}
	}
	int ans = INF;
	pair<int, int> fd = doors[0]; // fd: first door
	arr[fd.first][fd.second] = '*';
	for (int direction = 0; direction < 4; direction++) {
		for (int i = 1; i < n; i++) {
			int ny = fd.first + r[direction][0]*i;
			int nx = fd.second + r[direction][1]*i;
			if (!checkRange(ny, nx)) break;
			if (arr[ny][nx] == '*') break;
			if (arr[ny][nx] == '!') {
				pq.push({ ny, nx, 1, direction});
			}
			if (arr[ny][nx] == '#') {
				ans = 0;
			}
		}
	}
	while (!pq.empty() && ans == INF) {
		Info info = pq.top();
		pq.pop();
		//cout << info.y << ' ' << info.x << ' ' << info.n << endl;
		int d1 = 2 - info.direction / 2 * 2;
		int d2 = d1 + 1;
		for (int i = 1; i < n; i++) {
			int ny = info.y + r[d1][0] * i;
			int nx = info.x + r[d1][1] * i;
			if (!checkRange(ny, nx)) break;
			if (arr[ny][nx] == '*') break;
			if (arr[ny][nx] == '!') {
				pq.push({ ny, nx, info.n + 1, d1 });
			}
			if (arr[ny][nx] == '#') {
				ans = info.n;
				break;
			}
		}
		for (int i = 1; i < n; i++) {
			int ny = info.y + r[d2][0] * i;
			int nx = info.x + r[d2][1] * i;
			if (!checkRange(ny, nx)) break;
			if (arr[ny][nx] == '*') break;
			if (arr[ny][nx] == '!') {
				pq.push({ ny, nx, info.n + 1, d1 });
			}
			if (arr[ny][nx] == '#') {
				ans = info.n;
				break;
			}
		}
	}
	cout << ans << endl;
	return 0;
}
