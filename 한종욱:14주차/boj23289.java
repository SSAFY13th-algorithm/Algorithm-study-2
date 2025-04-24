import java.io.*;
import java.util.*;

public class boj23289 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 온풍기 방향별 이동 좌표 (dx[방향][방향세부])
    // 인덱스 1: 오른쪽, 2: 왼쪽, 3: 위쪽, 4: 아래쪽
    private static final int[][] dx = {
        {0},                    // 사용하지 않음 (인덱스 0)
        {0, 0, 0, -1, 1},       // 오른쪽 방향 (1): 추가 이동 방향들
        {0, 0, 0, -1, 1},       // 왼쪽 방향 (2): 추가 이동 방향들
        {0, 0, 0, -1, 0},       // 위쪽 방향 (3): 추가 이동 방향들
        {0, 0, 0, 0, 1}         // 아래쪽 방향 (4): 추가 이동 방향들
    };

    // 온풍기 방향별 이동 좌표 (dy[방향][방향세부])
    private static final int[][] dy = {
        {0},                    // 사용하지 않음 (인덱스 0)
        {0, 1, 0, 0, 0},        // 오른쪽 방향 (1): 주방향, 사용안함, 사용안함, 위대각선, 아래대각선
        {0, 0, -1, 0, 0},       // 왼쪽 방향 (2): 사용안함, 주방향, 사용안함, 위대각선, 아래대각선
        {0, 1, -1, 0, 0},       // 위쪽 방향 (3): 오른대각선, 왼대각선, 사용안함, 주방향, 사용안함
        {0, 1, -1, 0, 0}        // 아래쪽 방향 (4): 오른대각선, 왼대각선, 사용안함, 사용안함, 주방향
    };

    private static List<int[]> targets;   // 온도를 조사해야 하는 칸들의 위치 리스트
    private static List<Heater> heaters;  // 온풍기 리스트
    private static Cell[][] map;          // 격자 맵
    private static int r, c, k;           // r: 행 크기, c: 열 크기, k: 목표 온도

    public static void main(String[] args) throws IOException {
        init();  // 초기화
        int t = 0;  // 초기 시간

        // 반복 (최대 100초까지)
        while (t <= 100) {
            // 1단계: 온풍기에서 바람이 나옴
            for (Heater heater : heaters) {
                hotAirBlower(heater);
            }

            // 2단계: 온도가 조절됨
            map = controlTemperature();

            // 3단계: 바깥쪽 칸의 온도가 1씩 감소
            downTemperature();

            // 시간 증가
            t++;

            // 종료 조건: 모든 칸의 온도가 K 이상인지 확인
            if (valid()) break;
        }

        // 결과 출력 (100초가 넘으면 101 출력)
        int answer = t > 100 ? 101 : t;
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기화 함수: 입력을 받고 맵과 온풍기, 조사 대상 초기화
    private static void init() throws IOException {
        // 행, 열, 목표 온도 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());  // 행 크기
        c = Integer.parseInt(st.nextToken());  // 열 크기
        k = Integer.parseInt(st.nextToken());  // 목표 온도

        // 맵, 조사 대상, 온풍기 초기화
        map = new Cell[r + 1][c + 1];
        targets = new ArrayList<>();
        heaters = new ArrayList<>();

        // 격자 정보 입력
        for (int i = 1; i <= r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= c; j++) {
                map[i][j] = new Cell(Integer.parseInt(st.nextToken()));

                // 온풍기 정보 저장 (1~4: 온풍기 방향)
                if (map[i][j].status >= 1 && map[i][j].status <= 4) {
                    heaters.add(new Heater(i, j, map[i][j].status));
                }

                // 온도를 조사해야 하는 칸 저장 (5)
                if (map[i][j].status == 5) {
                    targets.add(new int[]{i, j});
                }
            }
        }

        // 벽 정보 입력
        int wall = Integer.parseInt(br.readLine());
        for (int i = 0; i < wall; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());  // 행
            int y = Integer.parseInt(st.nextToken());  // 열
            int w = Integer.parseInt(st.nextToken());  // 벽 종류 (0: 위쪽 벽, 1: 오른쪽 벽)

            // 위쪽 벽 설치
            if (w == 0) {
                map[x][y].hasWall = true;
                map[x][y].wall[3] = true;  // 현재 칸의 위쪽에 벽

                // 위쪽 칸의 아래쪽에도 벽 설정
                if (x - 1 > 0) {
                    map[x - 1][y].hasWall = true;
                    map[x - 1][y].wall[4] = true;  // 위 칸의 아래쪽에 벽
                }
            }

            // 오른쪽 벽 설치
            if (w == 1) {
                map[x][y].hasWall = true;
                map[x][y].wall[1] = true;  // 현재 칸의 오른쪽에 벽

                // 오른쪽 칸의 왼쪽에도 벽 설정
                if (y + 1 <= c) {
                    map[x][y + 1].hasWall = true;
                    map[x][y + 1].wall[2] = true;  // 오른쪽 칸의 왼쪽에 벽
                }
            }
        }
    }

    // 온풍기 바람 퍼뜨리는 함수
    private static void hotAirBlower(Heater heater) {
        boolean[][] visited = new boolean[r + 1][c + 1];  // 방문 체크 배열
        List<int[]> addable = new ArrayList<>();  // 온도를 추가할 칸 리스트
        Queue<int[]> q = new ArrayDeque<>();  // BFS를 위한 큐
        int temperature = 5;  // 시작 온도

        // 시작 위치 (온풍기 앞 칸)
        int[] start = new int[]{
            heater.x + dx[heater.dir][heater.dir],  // 온풍기 방향으로 한 칸 이동한 x좌표
            heater.y + dy[heater.dir][heater.dir],  // 온풍기 방향으로 한 칸 이동한 y좌표
            temperature  // 초기 온도
        };

        addable.add(start);  // 온도를 추가할 칸에 추가
        q.add(start);  // 큐에 추가

        // 온도가 남아있는 동안 (5, 4, 3, 2, 1)
        while (temperature-- > 0) {
            List<int[]> blank = new ArrayList<>();  // 이번 온도에서 확산될 새로운 칸 리스트

            // 현재 큐의 모든 위치에서 확산 처리
            while (!q.isEmpty()) {
                int[] current = q.poll();

                // 4가지 방향으로 확산 검사 (1: 오른쪽, 2: 왼쪽, 3: 위쪽, 4: 아래쪽)
                for (int i = 1; i <= 4; i++) {
                    // 현재 칸에 해당 방향으로 벽이 있으면 확산 불가
                    if (map[current[0]][current[1]].wall[i]) continue;

                    // 온풍기 주 방향으로 확산
                    if (i == heater.dir) {
                        int nx = current[0] + dx[heater.dir][i];
                        int ny = current[1] + dy[heater.dir][i];

                        // 맵 밖으로 나가면 무시
                        if (OOB(nx, ny)) continue;
                        blank.add(new int[]{nx, ny, temperature});
                    }
                    // 대각선 방향으로 확산
                    else {
                        int nx = current[0] + dx[heater.dir][i];
                        int ny = current[1] + dy[heater.dir][i];

                        // 맵 밖이거나, 확산 경로에 벽이 있거나, 자기 자신이면 무시
                        if (OOB(nx, ny)) continue;
                        if (map[nx][ny].wall[heater.dir]) continue;
                        if (current[0] == nx && current[1] == ny) continue;

                        // 대각선으로 한 칸 더 이동
                        nx += dx[heater.dir][heater.dir];
                        ny += dy[heater.dir][heater.dir];
                        if (OOB(nx, ny)) continue;
                        blank.add(new int[]{nx, ny, temperature});
                    }
                }
            }

            // 이번에 확산된 칸들 처리
            for (int[] element : blank) {
                // 이미 방문한 칸은 중복 처리 방지
                if (visited[element[0]][element[1]]) continue;
                visited[element[0]][element[1]] = true;
                addable.add(element);  // 온도를 추가할 칸에 추가
                q.add(element);  // 다음 확산을 위해 큐에 추가
            }
        }

        // 최종적으로 모든 칸에 온도 적용
        for (int[] element : addable) {
            map[element[0]][element[1]].temperature += element[2];
        }
    }

    // 맵 범위 체크 함수 (Out Of Bounds)
    private static boolean OOB(int nx, int ny) {
        return nx < 1 || nx > r || ny < 1 || ny > c;
    }

    // 온도 조절 함수
    private static Cell[][] controlTemperature() {
        // 원본 맵 복사 (동시에 온도 조절을 적용하기 위해)
        Cell[][] copyMap = new Cell[r + 1][c + 1];

        // 맵 초기화 (상태, 벽 정보 복사)
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                copyMap[i][j] = new Cell(map[i][j].status);
                copyMap[i][j].hasWall = map[i][j].hasWall;

                // 벽 정보 복사
                for (int k = 1; k <= 4; k++) {
                    copyMap[i][j].wall[k] = map[i][j].wall[k];
                }
            }
        }

        // 온도 조절
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                // 온도가 0인 칸은 조절할 필요 없음
                if (map[i][j].temperature == 0) continue;

                int t = 0;  // 현재 칸에서 줄어드는 온도

                // 인접한 4방향 체크
                for (int d = 1; d <= 4; d++) {
                    int nx = i + dx[d][d];
                    int ny = j + dy[d][d];

                    // 벽이 있거나 맵 밖이면 조절 불가
                    if (map[i][j].wall[d]|| OOB(nx, ny)) continue;

                    // 인접한 칸과의 온도 차이
                    int temperature = map[i][j].temperature - map[nx][ny].temperature;

                    // 현재 칸이 더 온도가 높으면 조절
                    if (temperature > 0) {
                        temperature /= 4;  // 온도 차이의 4분의 1만큼 조절
                        copyMap[nx][ny].temperature += temperature;  // 인접 칸 온도 증가
                        t += temperature;  // 현재 칸에서 줄어든 온도 누적
                    }
                }

                // 현재 칸의 최종 온도 계산 (원래 온도 - 줄어든 온도)
                copyMap[i][j].temperature += (map[i][j].temperature - t);
            }
        }

        return copyMap;  // 조절된 온도 맵 반환
    }

    // 외곽 칸 온도 감소 함수
    private static void downTemperature() {
        // 왼쪽 외곽과 오른쪽 외곽
        for (int i = 1; i <= r; i++) {
            // 왼쪽 외곽 (열 = 1)
            if (map[i][1].temperature > 0) {
                map[i][1].temperature--;
            }

            // 오른쪽 외곽 (열 = c)
            if (map[i][c].temperature > 0) {
                map[i][c].temperature--;
            }
        }

        // 위쪽 외곽과 아래쪽 외곽 (왼쪽, 오른쪽 모서리 제외)
        for (int i = 2; i <= c - 1; i++) {
            // 위쪽 외곽 (행 = 1)
            if (map[1][i].temperature > 0) {
                map[1][i].temperature--;
            }

            // 아래쪽 외곽 (행 = r)
            if (map[r][i].temperature > 0) {
                map[r][i].temperature--;
            }
        }
    }

    // 목표 온도 달성 체크 함수
    private static boolean valid() {
        // 모든 조사 대상 칸을 확인
        for (int[] element : targets) {
            // 하나라도 목표 온도보다 낮으면 false
            if (map[element[0]][element[1]].temperature < k) return false;
        }
        // 모든 칸이 목표 온도 이상이면 true
        return true;
    }

    // 격자 칸 클래스
    static class Cell {
        int status;         // 칸의 상태 (0: 빈칸, 1~4: 온풍기 방향, 5: 온도 조사 칸)
        int temperature;    // 현재 온도
        boolean hasWall;    // 벽 존재 여부
        boolean[] wall = new boolean[5];  // 각 방향별 벽 정보 (1: 오른쪽, 2: 왼쪽, 3: 위쪽, 4: 아래쪽)

        Cell(int status) {
            this.status = status;
            this.temperature = 0;  // 초기 온도는 0
            this.hasWall = false;  // 초기에는 벽 없음
        }
    }

    // 온풍기 클래스
    static class Heater {
        int x;    // 온풍기 행 위치
        int y;    // 온풍기 열 위치
        int dir;  // 온풍기 방향 (1: 오른쪽, 2: 왼쪽, 3: 위쪽, 4: 아래쪽)

        Heater(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
}