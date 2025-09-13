#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <map>
#include <string>
#define INF 987654321
#define lld long long

using namespace std;

lld n;
vector<lld> v;
vector<lld> ans;
lld max_diff;

lld real_index(lld a, lld b, lld index) {
	if(index >= a) index++;
	if(index >= b) index++;
	return index;
}

/*
이분 탐색.
a+b+c = 0을 만드는 c에 가까운 값을 찾는다.
배열에 a, b를 제외한 값을 찾아야 하니 이분탐색 알고리즘에서 a, b를 제외했다.
*/
lld find_c(const int a, const int b) {
	lld c = -(v[a]+v[b]);
	lld left = 0, right = n - 2;
	while(left <= right) {
		lld mid = (left + right) / 2;
		if(c < (lld)v[real_index(a, b, mid)]) {
			right = mid - 1;
		} else if (c > (lld)v[real_index(a, b, mid)]) {
			left = mid + 1;
		} else {
			return v[real_index(a, b, mid)];
		}
	}
	if(left >= n-2) left = n-3;
	
	lld q = real_index(a, b, left);
	lld w = real_index(a, b, left-1);
	// w = max(w, 0ll);
	if(w<0) return v[q];
	lld qq = abs((lld)v[q] - c);
	lld ww = abs((lld)v[w] - c);
	if(qq < ww) return v[q];
	// cout << w << endl;
	return v[w];
}

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	// 두 수 a, b를 잡고 a+b+c = 0을 만드는 c를 찾는다.
	cin >> n;
	v = vector<lld>(n);
	ans = vector<lld>(3);
	max_diff = __LONG_LONG_MAX__;
	for(int i = 0; i < n; i++) cin >> v[i];
	sort(v.begin(), v.end()); // 이분 탐색을 위한 정렬

	for(int i = 0; i < n; i++) {
		for(int j = i+1; j < n; j++) {
			lld c = find_c(i, j); // 두 지점을 잡고 0을 만드는 나머지 값을 이분 탐색
			lld diff = abs((lld)v[i] + (lld)v[j] + (lld)c);
			
			if(diff < max_diff) {
				max_diff = diff;
				ans[0] = v[i];
				ans[1] = v[j];
				ans[2] = c;
			}
		}
	}
	sort(ans.begin(), ans.end());
	cout << ans[0] << ' ' << ans[1] << ' ' << ans[2];
	return 0;
}
