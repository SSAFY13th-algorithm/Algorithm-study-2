#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <map>
#define INF 987654321

using namespace std;

int n, m;
vector<int> v;
vector<int> s;
vector<vector<int>> dp;

int main()
{
    ios_base::sync_with_stdio(false);cout.tie(NULL);cin.tie(NULL);
    cin >> n >> m;
    v = vector<int>(n);
    s = vector<int>(n+1);
    dp = vector<vector<int>>(n+1, vector<int>(n/2+1, -INF));
    for(int i = 0; i < n; i++) {
        cin >> v[i];
        s[i+1] = s[i] + v[i];
    }
    dp[1][1] = s[1];
    for(int i = 2; i <= n; i++) {
        dp[i][1] = dp[i-1][1];
        for(int j = 0; j < i; j++) {
            dp[i][1] = max(dp[i][1], s[i]-s[j]);
        }
    }

    for(int i = 3; i <= n; i++) {
        for(int j = 2; j <= m; j++) {
            dp[i][j] = dp[i-1][j];
            for(int k = 1; k <= i-2; k++) {
                dp[i][j] = max(dp[i][j], dp[k][j-1]+s[i]-s[k+1]);
            }
        }
    }
    cout << dp[n][m];
    return 0;
}
/*
         -1    3    1    2    4   -1
dp[n][1] -1    3    4    6    10  10
dp[n][2] -INF  -INF 0    5    8   
*/
