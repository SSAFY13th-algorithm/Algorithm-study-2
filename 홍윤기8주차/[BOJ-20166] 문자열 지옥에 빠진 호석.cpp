#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#include <sstream>
#define INF 987654321
#define lld long long

#define MAX_N 10
#define MAX_M 10
#define MAX_K 1000
#define MAX_K_LEN 5

using namespace std;

char arr[MAX_N][MAX_M];
int cache[MAX_N][MAX_M][MAX_K_LEN];
vector<string> words;
int N, M, K;
int ANS = 0;
int r[8][2] = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1 } };

pair<int, int> nextCoord(int y, int x, const int direction) {
	y = y + r[direction][0];
	x = x + r[direction][1];
	y = (y + N) % N;
	x = (x + M) % M;
	return { y, x };
}

int dfs(const int y, const int x, const string& godWord, vector<char>& v) {
	v.push_back(arr[y][x]);
	int& ret = cache[y][x][v.size() - 1];

	// 신의 뜻과 다르다면
	if (v.back() != godWord[v.size()-1]) {
		v.pop_back();
		return ret = 0;
	}
	else if (v.size() == godWord.size()) {
		v.pop_back();
		return ret = 1;
	}


	if (ret >= 0) {
		v.pop_back();
		return ret;
	}
	ret = 0;

	for (int i = 0; i < 8; i++) {
		pair<int, int> coord = nextCoord(y, x, i);
		ret += dfs(coord.first, coord.second, godWord, v);
	}
	v.pop_back();
	return ret;
}

void solve() {
	for (vector<string>::iterator iter = words.begin(); iter != words.end(); iter++) {
		const string& godWord = *iter;

		// Initialize variables
		ANS = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int k = 0; k < 5; k++) {
					cache[i][j][k] = -1;
				}
			}
		}

		// DFS
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				vector<char> v;
				ANS += dfs(i, j, godWord, v);
			}
		}

		cout << ANS << '\n';
	}
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);

	cin >> N >> M >> K;
	for (int i = 0; i < N; i++) {
		string s;
		cin >> s;
		for (int j = 0; j < M; j++) {
			arr[i][j] = s[j];
		}
	}
	words = vector<string>(K);
	for (int i = 0; i < K; i++) {
		cin >> words[i];
	}
	solve();
	
	return 0;
}
