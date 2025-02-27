#include <iostream>
#include <math.h>
#include <algorithm>
#include <string>
#include <vector>
#define INF 987654321

using namespace std;

int n, m;

int main() {
    ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    cin >> n >> m;
    vector<int> v(n);
    for(int i = 0; i < n; i++) cin >> v[i];
    sort(v.begin(), v.end());
    int ans = 0;
    while (!v.empty())
    {
        int t = v.back();
        v.pop_back();
        ans += t;
        t = t*(m-1);
        while(t>0 && !v.empty()) {
            t -= v.back();
            v.pop_back();
        }
    }
    cout << ans << endl;    


    return 0;
}
