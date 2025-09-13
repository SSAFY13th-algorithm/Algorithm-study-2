#include <iostream>
#include <algorithm>
#include <vector>
#define INF 987654321

using namespace std;

int n;
int minDay, maxDay;
vector<pair<int, int>> v;
int arr[366][1000];
int scheduleN[366];

bool customSort(const pair<int, int>& a, const pair<int, int>& b) {
	if (a.first == b.first) {
		return a.second > b.second;
	}
	return a.first < b.first;
}

int main()
{
	ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
	cin >> n;
	minDay = INF; maxDay = 0;
	for (int i = 0; i < n; i++) {
		int a, b;
		cin >> a >> b;
		v.push_back({ a, b });
		minDay = min(a, minDay);
		maxDay = max(b, maxDay);
	}
	// customSort는 달력에 주어진 규칙에 맞게 달력을 직접 메모리에 올리기 위해 정렬한다.
	// Greedy로 달력에 일정을 삽입할 것이기 때문에 이렇게 정렬한다.
	sort(v.begin(), v.end(), customSort);

	// arr에 실제로 달력 그리기
	for (vector<pair<int, int>>::iterator iter = v.begin(); iter != v.end(); iter++) {
		int a = iter->first, b = iter->second;
		for (int i = 0; i < 1000; i++) {
			if (arr[a][i] == 0) {
				for (int j = a; j <= b; j++) {
					arr[j][i] = 1;
				}
				break;
			}
		}
	}

	// 1일부터 365일까지 각 날에 일정 갯수 카운트.
	// 만약 해당일에 일정이 없으면 끊어진 것이라고 봐도 된다.
	for (int i = 0; i < n; i++) {
		for (int j = minDay; j <= maxDay; j++) {
			scheduleN[j] += arr[j][i];
		}
	}

	// "연속된 일정"의 가로와 최대 일정 갯수를 서로 곱한 것이 코팅지 사이즈다.
	// 각 연속된 일정의 구간과 해당 구간의 최대 일정 갯수(높이)를 구하여 모두 합한다.
	int ans = 0, left = -1, maxSchedule = 0;
	for (int right = minDay; right <= maxDay; right++) {
		maxSchedule = max(maxSchedule, scheduleN[right]);
		if (left == -1 && scheduleN[right] != 0) { 
			left = right - 1;
		}
		if (right == maxDay || scheduleN[right+1] == 0) {
			ans += maxSchedule * (right - left);
			maxSchedule = 0;
			left = -1;
		}
	}
	cout << ans << endl;
	return 0;
}
