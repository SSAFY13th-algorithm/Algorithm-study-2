#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <map>
#define INF 987654321

using namespace std;

int R, C, T;
int arr[50][50];
int r[4][2] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
int upcycle[4][2] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
int downcycle[4][2] = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
int cleaner = -1;

bool checkRange(int y, int x) {
    return y >= 0 && x >= 0 && y < R && x < C;
}

void airwork() {
    int y = cleaner-1;
    int x = 0;
    int direction = 0;
    while(direction < 4) {
        int ny = y + upcycle[direction][0];
        int nx = x + upcycle[direction][1];
        if(!checkRange(ny, nx) || ny > cleaner) {
            direction++;
        } else {
            if(arr[ny][nx] == -1) {
                arr[y][x] = 0;
                break;
            }
            arr[y][x] = arr[ny][nx];
            y = ny;
            x = nx;
        }
    }
    y = cleaner+2;
    x = 0;
    direction = 0;
    while(direction < 4) {
        int ny = y + downcycle[direction][0];
        int nx = x + downcycle[direction][1];
        if(!checkRange(ny, nx) || ny < cleaner+1) {
            direction++;
        } else {
            if(arr[ny][nx] == -1) {
                arr[y][x] = 0;
                break;
            }
            arr[y][x] = arr[ny][nx];
            y = ny;
            x = nx;
        }
    }
}

void diffusion() {
    vector<pair<int, pair<int, int>>> pm;
    for(int i = 0; i < R; i++) {
        for(int j = 0; j < C; j++) {
            if(arr[i][j] > 0) pm.push_back({arr[i][j], {i, j}});
        }
    }
    for(vector<pair<int, pair<int, int>>>::iterator iter = pm.begin(); iter != pm.end(); iter++) {
        int y = iter->second.first, x = iter->second.second;
        int k = iter->first;
        int diffusionCnt = 0;
        for(int i = 0; i < 4; i++) {
            int ny = y + r[i][0];
            int nx = x + r[i][1];
            if(!checkRange(ny, nx) || arr[ny][nx] == -1) continue;
            diffusionCnt++;
            arr[ny][nx] += k/5;
        }
        arr[y][x] -= (k/5)*diffusionCnt;
    }
}

int main()
{
    ios_base::sync_with_stdio(false);cout.tie(NULL);cin.tie(NULL);
    cin >> R >> C >> T;
    for(int i = 0; i < R; i++) {
        for(int j = 0; j < C; j++) {
            cin >> arr[i][j];
            if(arr[i][j] == -1 && cleaner == -1) cleaner = i;
        }
    }

    for(int i = 0; i < T; i++) {
        diffusion();
        airwork();
    }
    int ans = 2;

    for(int i = 0; i < R; i++) {
        for(int j = 0; j < C; j++) {
            ans += arr[i][j];
        }
    }
    cout << ans;
    return 0;
}
