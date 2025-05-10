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

int n, m;
int arr[501];

int getParent(int a) {
	if (a != arr[a]) return arr[a] = getParent(arr[a]);
	return a;
}

void unionNode(int a, int b) {
	int pa = getParent(a);
	int pb = getParent(b);
	if (pa != pb) arr[pa] = pb;
}

int main() 
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	for (int test_case = 1;;test_case++) {
		cin >> n >> m;
		if (n == 0 && m == 0) break;

		for (int i = 1; i <= n; i++) arr[i] = i; // 집합 초기화
		vector<int> v; // 사이클을 발생하는 노드
		for (int i = 0; i < m; i++) {
			int a, b; cin >> a >> b;
			int pa = getParent(a);
			int pb = getParent(b);
			if (pa == pb) v.push_back(pa); // 사이클을 발생하는 노드 삽입
			unionNode(a, b);
		}
		map<int, bool> mp; // map으로 집합을 셀 수 있다.
		for (int i = 1; i <= n; i++) {
			int p = getParent(i);
			if (mp.find(p) == mp.end()) mp.insert({ p, true });
		}
		for (int i = 0; i < v.size(); i++) { // 사이클을 발생하는 노드들
			int p = getParent(v[i]); // 해당 노드의 부모 노드가 map에 있다면
			if (mp.find(p) != mp.end()) {
				mp.erase(p); // 지운다
			}
		}
		string msg;
		if (mp.size() == 0) {
			msg = "No trees.";
		}
		else if (mp.size() == 1) {
			msg = "There is one tree.";
		}
		else {
			msg = "A forest of " + to_string(mp.size()) + " trees.";
		}
		cout << "Case " << test_case << ": " << msg << endl;
	}
	return 0;
}
