#include <iostream>
#include <vector>
using namespace std;

int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int N, M;
	cin >> N;
	vector<int> A(N);
	for (int &i : A) cin >> i;
	cin >> M;
	vector<int> B(M);
	for (int &i : B) cin >> i;

	vector<int> R;
	int idxA = 0, idxB = 0;
	while (idxA < N && idxB < M) {
		bool suc = 0;
		for (int x = 100; x >= 1; x--) {
			int res = 0;
			int new_idxA = idxA, new_idxB = idxB;
			for (int i = idxA; i < N; i++) if (A[i] == x) { res++; new_idxA = i + 1; break; }
			for (int i = idxB; i < M; i++) if (B[i] == x) { res++; new_idxB = i + 1; break; }
			if (res == 2) {
				idxA = new_idxA, idxB = new_idxB;
				R.push_back(x);
				suc = 1;
				break;
			}
		}
		if (!suc) break;
	}
	cout << R.size() << '\n';
	for (int i : R) cout << i << ' ';

}
