#include <iostream>
#include <math.h>
#include <algorithm>
#include <string>
#include <vector>
#include <queue>
#define INF 987654321

using namespace std;

int n;
int arr[101][101];
int result[101];
vector<int> v[2];
int visited[101];

void solve(int x, int s) { // 1 0 v
    v[s].push_back(x);
    visited[x] = 1;
    for(int i = 1; i <= n; i++) {
        if(arr[x][i] == 1 && !visited[i]) {
            solve(i, 1-s);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    cin >> n;
    for(int i = 1; i <= n; i++) {
        int k = 0;
        cin >> k;
        for(int j = 0; j < k; j++) {
            int x = 0;
            cin >> x;
            arr[i][x] = arr[x][i] = 1;
        }
    }
    for(int i = 1; i <= n; i++) if(arr[1][i] == 0) result[i] = 1;

    for(int i = 1; i <= n; i++) {
        if(!visited[i]) {
            solve(i, 0);
        }
    }
    sort(v[0].begin(), v[0].end());
    sort(v[1].begin(), v[1].end());
    cout << v[0].size() << endl;
    for(int i = 0; i < v[0].size(); i++) cout << v[0][i] << ' ';
    cout << endl;
    cout << v[1].size() << endl;
    for(int i = 0; i < v[1].size(); i++) cout << v[1][i] << ' ';
    return 0;
}
