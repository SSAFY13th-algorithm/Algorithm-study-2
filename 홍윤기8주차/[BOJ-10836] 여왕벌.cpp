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

int M, N;
int arr[MAX_M][MAX_M];

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> M >> N;
	vector<int> v(2*M-1);
	for (int i = 0; i < M; i++) for (int j = 0; j < M; j++)arr[i][j] = 1;
	for (int i = 0; i < N; i++) {
		int a, b, c, index = 0;
		cin >> a >> b >> c;
		for (int j = 0; j < a; j++) v[index++] = 0;
		for (int j = 0; j < b; j++) v[index++] = 1;
		for (int j = 0; j < c; j++) v[index++] = 2;
		index = 0;
		// set L grow
		for (int j = M - 1; j >= 0; j--) {
			arr[j][0] += v[index++];
		}
		for (int j = 1; j < M; j++) {
			arr[0][j] += v[index++];
		}
	}
	for (int i = 1; i < M; i++) {
		for (int j = 1; j < M; j++) {
			arr[i][j] = max(arr[i - 1][j - 1], max(arr[i - 1][j], arr[i][j - 1]));
		}
	}
	for (int i = 0; i < M; i++) {
		for (int j = 0; j < M; j++) {
			cout << arr[i][j] << ' ';
		}cout << '\n';
	}
	
	return 0;
}
