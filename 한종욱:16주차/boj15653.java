import java.io.*;
import java.util.*;

public class boj15653 {
    // 입출력 스트림 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 상하좌우 방향 이동을 위한 배열 (동, 남, 서, 북)
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    private static char[][] map;  // 게임 맵
    private static boolean[][][][] visited;  // 방문 상태 배열 (빨간 구슬 x, y, 파란 구슬 x, y)
    private static Ball red, blue;  // 빨간 구슬과 파란 구슬의 위치
    private static int n, m;  // 맵의 크기

    public static void main(String[] args) throws IOException {
        init();  // 초기화

        int answer = BFS();  // BFS 탐색으로 최소 이동 횟수 찾기
        bw.write(answer + "\n");  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기화 함수
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 행 개수
        m = Integer.parseInt(st.nextToken());  // 열 개수

        map = new char[n][m];  // 맵 초기화
        visited = new boolean[n][m][n][m];  // 방문 배열 초기화 (빨간 구슬 x,y, 파란 구슬 x,y)

        // 맵 정보 입력 및 구슬 위치 저장
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'R') red = new Ball(i, j);  // 빨간 구슬 위치
                if (map[i][j] == 'B') blue = new Ball(i, j);  // 파란 구슬 위치
            }
        }
    }

    // BFS 탐색 함수 - 최소 이동 횟수 구하기
    private static int BFS() {
        Queue<Status> q = new ArrayDeque<>();  // BFS를 위한 큐
        int result = -1;  // 결과값 초기화 (실패시 -1 반환)

        // 시작 상태 방문 표시 및 큐 추가
        visited[red.x][red.y][blue.x][blue.y] = true;
        q.add(new Status(red, blue, 0));

        while (!q.isEmpty()) {
            Status current = q.poll();  // 현재 상태

            // 게임 종료 여부 확인
            int flag = finish(current);
            if (flag == 2) continue;  // 파란 구슬이 구멍에 빠졌으면 실패, 다음 상태 확인
            if (flag == 1) {  // 빨간 구슬만 구멍에 빠졌으면 성공
                result = current.count;  // 이동 횟수 저장
                break;
            }

            // 4방향으로 기울이기 시도
            for (int i = 0; i < 4; i++) {
                // 새로운 구슬 객체 생성 (현재 상태 복사)
                Ball newRed = new Ball(current.red.x, current.red.y);
                Ball newBlue = new Ball(current.blue.x, current.blue.y);

                // 기울이는 방향에 따라 어떤 구슬을 먼저 이동시킬지 결정
                int pr = priority(current.red, current.blue, i);
                if (pr == 0) {  // 빨간 구슬 먼저 이동
                    newRed = move(newRed, newBlue, i, pr, 'R');
                    newBlue = move(newRed, newBlue, i, pr, 'B');
                } else if (pr == 1) {  // 파란 구슬 먼저 이동
                    newBlue = move(newRed, newBlue, i, pr, 'B');
                    newRed = move(newRed, newBlue, i, pr, 'R');
                }

                // 구슬이 구멍에 빠진 경우는 방문 체크하지 않고 큐에 추가
                if ((newRed.x == -1 && newRed.y == -1) || (newBlue.x == -1 && newBlue.y == -1)) {
                    q.add(new Status(newRed, newBlue, current.count + 1));
                    continue;
                }

                // 이미 방문한 상태면 건너뜀
                if (visited[newRed.x][newRed.y][newBlue.x][newBlue.y]) continue;

                // 새로운 상태 방문 표시 및 큐에 추가
                visited[newRed.x][newRed.y][newBlue.x][newBlue.y] = true;
                q.add(new Status(newRed, newBlue, current.count + 1));
            }
        }
        return result;  // 최종 결과 반환
    }

    // 기울일 때 어떤 구슬을 먼저 이동시킬지 결정하는 함수
    // 0: 빨간 구슬 먼저 또는 상관없음, 1: 파란 구슬 먼저
    private static int priority(Ball red, Ball blue, int dir) {
        if (dir == 0) {  // 동쪽으로 기울이기 (오른쪽)
            int redX = red.x;
            int blueX = blue.x;

            if (redX < blueX) return 1;  // 파란 구슬이 더 오른쪽에 있으면 파란 구슬 먼저
            return 0;  // 그렇지 않으면 빨간 구슬 먼저
        } else if (dir == 1) {  // 남쪽으로 기울이기 (아래쪽)
            int redY = red.y;
            int blueY = blue.y;

            if (redY > blueY) return 0;  // 빨간 구슬이 더 아래쪽에 있으면 빨간 구슬 먼저
            return 1;  // 그렇지 않으면 파란 구슬 먼저
        } else if (dir == 2) {  // 서쪽으로 기울이기 (왼쪽)
            int redX = red.x;
            int blueX = blue.x;

            if (redX > blueX) return 1;  // 파란 구슬이 더 왼쪽에 있으면 파란 구슬 먼저
            return 0;  // 그렇지 않으면 빨간 구슬 먼저
        } else {  // 북쪽으로 기울이기 (위쪽)
            int redY = red.y;
            int blueY = blue.y;

            if (redY < blueY) return 0;  // 빨간 구슬이 더 위쪽에 있으면 빨간 구슬 먼저
            return 1;  // 그렇지 않으면 파란 구슬 먼저
        }
    }

    // 구슬 이동 함수
    private static Ball move(Ball red, Ball blue, int dir, int pr, char moveColor) {
        int x = 0;
        int y = 0;

        if (moveColor == 'R') {  // 빨간 구슬 이동
            x = red.x;
            y = red.y;

            while (true) {
                int nx = x + dx[dir];  // 다음 위치 계산
                int ny = y + dy[dir];

                // 벽을 만나거나 다른 구슬이 있는 경우 이동 중단
                if (map[nx][ny] == '#' || (pr == 1 && nx == blue.x && ny == blue.y)) break;

                x = nx;  // 위치 갱신
                y = ny;

                if (map[x][y] == 'O') {  // 구멍에 빠진 경우
                    x = -1;  // 특수 값으로 설정
                    y = -1;
                    break;
                }
            }
        } else {  // 파란 구슬 이동
            x = blue.x;
            y = blue.y;

            while (true) {
                int nx = x + dx[dir];  // 다음 위치 계산
                int ny = y + dy[dir];

                // 벽을 만나거나 다른 구슬이 있는 경우 이동 중단
                if (map[nx][ny] == '#' || (pr == 0 && nx == red.x && ny == red.y)) break;

                x = nx;  // 위치 갱신
                y = ny;

                if (map[x][y] == 'O') {  // 구멍에 빠진 경우
                    x = -1;  // 특수 값으로 설정
                    y = -1;
                    break;
                }
            }
        }
        return new Ball(x, y);  // 새로운 위치의 구슬 반환
    }

    // 게임 종료 여부 확인 함수
    // 0: 계속 진행, 1: 성공(빨간 구슬만 구멍에 빠짐), 2: 실패(파란 구슬이 구멍에 빠짐)
    private static int finish(Status s) {
        if (s.blue.x == -1 && s.blue.y == -1) {  // 파란 구슬이 구멍에 빠진 경우
            return 2;  // 실패
        } else if (s.red.x == -1 && s.red.y == -1) {  // 빨간 구슬만 구멍에 빠진 경우
            return 1;  // 성공
        }
        return 0;  // 계속 진행
    }

    // 구슬 클래스 - 위치 정보 저장
    static class Ball {
        int x;  // 행 위치
        int y;  // 열 위치

        Ball(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 게임 상태 클래스 - 두 구슬의 위치와 이동 횟수 저장
    static class Status {
        Ball red;    // 빨간 구슬 위치
        Ball blue;   // 파란 구슬 위치
        int count;   // 이동 횟수

        Status(Ball red, Ball blue, int count) {
            this.red = new Ball(red.x, red.y);    // 깊은 복사
            this.blue = new Ball(blue.x, blue.y);  // 깊은 복사
            this.count = count;
        }
    }
}