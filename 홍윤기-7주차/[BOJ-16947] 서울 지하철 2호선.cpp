#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#define INF 987654321

using namespace std;

int n;
vector<int> v[3001];
vector<bool> isCircular;
vector<int> dist;
int leaf[3001];

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n;
	for (int i = 1; i <= n; i++) v[i] = vector<int>();
	isCircular = vector<bool>(n + 1);
	dist = vector<int>(n + 1, INF);
	for (int i = 0; i < n; i++) {
		int a, b; cin >> a >> b;
		v[a].push_back(b);
		v[b].push_back(a);
		leaf[a]++;
		leaf[b]++;
	}
	vector<bool> visited(n + 1);
	vector<int> path;
	vector<bool> cache(n + 1);
	path.push_back(1);
	visited[1] = true;
	cache[1] = true;
	while (!path.empty()) {
		int curr = path.back();
		vector<int> near = v[curr];
		bool isEnd = true;
		for (vector<int>::iterator iter = near.begin(); iter != near.end(); iter++) {
			if (visited[*iter] && path.size() >= 2 && path[path.size() - 2] != *iter && cache[*iter] == true && !isCircular[*iter]) {
				for (int i = path.size()-1; path[i] != *iter; i--) {
					isCircular[path[i]] = true;
					dist[path[i]] = 0;
				}
				isCircular[*iter] = true;
				dist[*iter] = 0;
			}
			if (!visited[*iter]) {
				path.push_back(*iter);
				cache[*iter] = true;
				visited[*iter] = true;
				isEnd = false;
				break;
			}
		}
		if (isEnd) {
			cache[path.back()] = false;
			path.pop_back(); 
		}
	}
	for (int node = 1; node <= n; node++) {
		if (leaf[node] == 1) {
			vector<int> path;
			vector<int> visited(n + 1);
			path.push_back(node);
			visited[node] = true;
			bool isDone = false;
			while (!isDone) {
				int curr = path.back();
				bool isEnd = true;
				for (vector<int>::iterator iter = v[curr].begin(); iter != v[curr].end(); iter++) {
					if (isCircular[*iter]) {
						isDone = true;
						break;
					}
					if (!visited[*iter]) {
						path.push_back(*iter);
						visited[*iter] = true;
						isEnd = false;
						break;
					}
				}
				if (isDone) break;
				if (isEnd) path.pop_back();
			}
			int cnt = 1;
			while (!path.empty()) {
				dist[path.back()] = min(dist[path.back()], cnt);
				cnt++;
				path.pop_back();
			}
		}
	}
	for (int node = 1; node <= n; node++) {
		cout << dist[node] << ' ';
	}
	return 0;
}
