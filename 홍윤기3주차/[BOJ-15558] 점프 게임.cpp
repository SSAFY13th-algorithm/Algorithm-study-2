#include <iostream>
#include <math.h>
#include <algorithm>
#include <string>
#include <vector>
#include <queue>
#define INF 987654321

using namespace std;

int cache[100000][2];

int main() {
    ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    int n, k;
    cin >> n >> k;
    string a, b;
    cin >> a >> b;
    queue<pair<int, pair<int, int>>> q;
    q.push({0, {0, 0}});
    bool complete = false;
    for(int i = 0; i < 100000; i++) cache[i][0] = cache[i][1] = INF;
    while(!q.empty()) {
        int x = q.front().second.first;
        int y = q.front().second.second;
        int t = q.front().first;
        q.pop();
        if(cache[x][y] <= t) continue;
        cache[x][y] = t;
        if(t>x) continue;

        if(x >= n-k) {
            complete = true;
            break;
        }
        if(y == 0 && a[x+1] == '1') {
            q.push({t+1, {x+1, 0}});
        } else if(y == 1 && b[x+1] == '1') {
            q.push({t+1, {x+1, 1}});
        }
        if(x > 1 && t < x - 1) {
            if(y == 0 && a[x-1] == '1') {
                q.push({t+1, {x-1, 0}});
            } else if(y == 1 && b[x-1] == '1') {
                q.push({t+1, {x-1, 1}});
            }
        }
        if(y == 0 && b[x+k] == '1') {
            q.push({t+1, {x+k, 1}});
        } else if(y == 1 && a[x+k] == '1') {
            q.push({t+1, {x+k, 0}});
        }
    }
    cout << complete;
    return 0;
}
