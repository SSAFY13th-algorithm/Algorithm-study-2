#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int D[101][51]{}, X[101][51]{}, S[101]{}, N, M;
const int INF = -1234567890;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	for (int i = 1; i <= 100; i++) for (int k = 1; k <= 50; k++) D[i][k] = INF, X[i][k] = INF;

	cin >> N >> M;
	for (int i = 1; i <= N; i++) cin >> S[i], S[i] += S[i - 1];

	D[1][1] = S[1], X[1][1] = S[1];
	for (int n = 2; n <= N; n++) {
		for (int i = 1; n - i >= 0; i++) D[n][1] = max(D[n][1], S[n] - S[n - i]);
		X[n][1] = max(X[n - 1][1], D[n][1]);
		for (int k = 2; k <= M; k++) {
			for (int i = 1; 2 * k - 2 <= n - i; i++) D[n][k] = max(D[n][k], X[n - i - 1][k - 1] + S[n] - S[n - i]);
			X[n][k] = max(X[n - 1][k], D[n][k]);
		}
	}
	cout << X[N][M];

}
