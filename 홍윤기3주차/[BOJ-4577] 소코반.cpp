#include <iostream>
#include <math.h>
#include <algorithm>
#include <string>
#include <vector>
#define INF 987654321

#define BLANK  (1<<0) // .
#define WALL   (1<<1) // #
#define TARGET (1<<2) // +
#define BLOCK  (1<<3) // b
#define PLAYER (1<<4) // w

#define UP 0
#define DOWN 1
#define LEFT 2
#define RIGHT 3

using namespace std;

typedef int ENTITY;

const int r[4][2] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // UDLR

bool moveable(int y, int x, int dir, ENTITY entity, int arr[][15]) {
    int ny = y + r[dir][0];
    int nx = x + r[dir][1];
    ENTITY frontEntity = arr[ny][nx];
    switch (entity)
    {
    case PLAYER:
        // 비어있거나, 블럭인데 블럭이 움직일 수 있거나
        return (frontEntity & BLANK) || ((frontEntity & BLOCK) && moveable(ny, nx, dir, BLOCK, arr)); // unreachable?
    case BLOCK: 
        // 비어있으면
        return (frontEntity == BLANK) || (frontEntity == (BLANK | TARGET));
    default:
        break;
    }
    return false;
}

bool movePlayer(int py, int px, int dir, int arr[][15]) {

    int ny = py + r[dir][0];
    int nx = px + r[dir][1];

    ENTITY frontEntity = arr[ny][nx];

    if(frontEntity & WALL) return false; // 앞이 벽이면
    if((frontEntity == BLANK) || (frontEntity == (BLANK | TARGET))) { // 앞이 비어있거나 타겟이면
        arr[py][px] ^= PLAYER;
        arr[ny][nx] |= PLAYER;
        return true;
    }
    if((frontEntity & BLOCK) && moveable(ny, nx, dir, BLOCK, arr)) { // 앞에 있는 블럭을 밀 수 있으면
        int nny = ny + r[dir][0];
        int nnx = nx + r[dir][1];
        arr[ny][nx] ^= BLOCK;
        arr[nny][nnx] |= BLOCK;

        arr[py][px] ^= PLAYER;
        arr[ny][nx] |= PLAYER;
        return true;
    }
    return false;
}

ENTITY getBitCode(const char c) {
    switch (c)
    {
    case '.':
        return BLANK;
    case '#':
        return WALL;
    case '+':
        return TARGET | BLANK;
    case 'w':
        return PLAYER | BLANK;
    case 'W':
        return PLAYER | TARGET | BLANK;
    case 'b':
        return BLOCK | BLANK;
    case 'B':
        return BLOCK | TARGET | BLANK;
    default:
        break;
    }
    return 0;
}

char getEntityChar(const ENTITY code) {
    if(code == BLANK)   return '.';
    if(code == WALL)    return '#';
    if(code & BLOCK)    return (code & TARGET) ? 'B' : 'b';
    if(code & PLAYER)   return (code & TARGET) ? 'W' : 'w';
    if(code & TARGET)  return '+';
    return '?';
}

bool checkFinished(ENTITY arr[][15], int n, int m) {
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < m; j++) {
            if(arr[i][j] & TARGET) {
                if(!(arr[i][j] & BLOCK)) {
                    return false;
                }
            }
        }
    }
    return true;
}

bool solve(int py, int px, const string cmds, ENTITY arr[][15], int n, int m) {
    int DIR[125] = {0};
    bool finished = false;
    DIR['U'] = UP; DIR['D'] = DOWN; DIR['L'] = LEFT; DIR['R'] = RIGHT;
    for(int i = 0; i < cmds.length() && !finished; i++) {
        const char cmd = cmds[i];
        const int dir = DIR[cmd];
        bool moved = movePlayer(py, px, dir, arr);
        if(moved) {
            py += r[dir][0];
            px += r[dir][1];
        }
        finished = checkFinished(arr, n, m); // error occur at this code
    }
    return finished;
}

int main() {
    ios_base::sync_with_stdio(false); cin.tie(NULL); cout.tie(NULL);
    int game_case = 1;
    while(true) {
        int n, m, py, px;
        cin >> n >> m;
        ENTITY arr[15][15] = {0};
        if(n == 0 && m == 0) return 0;
        for(int i = 0; i < n; i++) {
            string s;
            cin >> s;
            for(int j = 0; j < m; j++) {
                arr[i][j] = getBitCode(s[j]);
                if(arr[i][j] & PLAYER) {
                    py = i;
                    px = j;
                }
            }
        }
        string cmds;
        cin >> cmds;
        bool complete = solve(py, px, cmds, arr, n, m);
        string msg = complete ? "complete" : "incomplete";
        cout << "Game " << game_case << ": " << msg << '\n';
        for(int i = 0; i < n; i++, cout << '\n') {
            for(int j = 0; j < m; j++) {
                cout << getEntityChar(arr[i][j]);
                // cout << (arr[i][j] & BLANK ? '.' : '#');
            }
        }
        game_case++;
    }
    return 0;
}
