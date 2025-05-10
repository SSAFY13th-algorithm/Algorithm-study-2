#include <iostream>
#include <math.h>
#include <algorithm>
#include <string>
#include <vector>
#include <queue>
#define INF 987654321

using namespace std;

int main() {
    ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    int n = 1000;
    cin >> n;
    vector<int> v(n);
    for(int i = 0; i < n; i++) {
        cin >> v[i];
    }
    int dp[1000] = {0};
    for(int i = 0; i < n; i++) {
        for(int j = 0; j <= i; j++) {
            if(j>=1)
                dp[i] = max(dp[i], abs(v[i]-v[j]) + dp[j-1]);
            else
                dp[i] = abs(v[i]-v[j]);
        }
    }
    cout << dp[n-1] << endl;
    return 0;
}
