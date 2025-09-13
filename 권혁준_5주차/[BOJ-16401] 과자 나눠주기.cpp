#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;


int main()
{
	cin.tie(0)->sync_with_stdio(0);

	int M, N;
	cin >> M >> N;
	vector<int> A(N);
	for (int &i : A) cin >> i;
	
	int s = 0, e = 1e9, m = (s + e + 1) >> 1;
	while (s < e) {
		int r = 0;
		for (int &i : A) r += i / m;
		if (r >= M) s = m;
		else e = m - 1;
		m = (s + e + 1) >> 1;
	}
	cout << m;

}
