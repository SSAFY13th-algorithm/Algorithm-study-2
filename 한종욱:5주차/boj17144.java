import java.io.*;
import java.util.*;

public class boj17144 {
    // 입출력을 위한 BufferedReader
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 방의 행(r), 열(c), 시간(t)
    private static int r, c, t;

    // 미세먼지 상태를 저장하는 2차원 배열
    private static int[][] room;

    // 공기청정기 객체
    private static final AirCleaner airCleaner = new AirCleaner();

    // 상하좌우 이동을 위한 방향 배열
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        init();                     // 초기 입력 처리
        simulate();                 // t초 동안 시뮬레이션 실행
        System.out.println(sum());  // 남은 미세먼지의 양 출력
    }

    // 초기 입력을 처리하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        room = new int[r][c];

        // 방의 상태와 공기청정기 위치 입력
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());
                if (room[i][j] == -1) {
                    airCleaner.setCleaner(i, j);  // 공기청정기 위치 저장
                }
            }
        }
    }

    // t초 동안 시뮬레이션을 실행하는 메소드
    private static void simulate() {
        for (int i = 0; i < t; i++) {
            diffusion();    // 미세먼지 확산
            runCleaner();   // 공기청정기 작동
        }
    }

    // 미세먼지 확산을 처리하는 메소드
    private static void diffusion() {
        int[][] empty = new int[r][c];  // 확산되는 미세먼지를 임시 저장할 배열

        // 모든 칸의 미세먼지 확산 처리
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int[] current = new int[]{i, j};
                int temp = room[current[0]][current[1]] / 5;  // 확산되는 양

                // 4방향으로 확산 시도
                for (int k = 0; k < 4; k++) {
                    int nx = current[0] + dx[k];
                    int ny = current[1] + dy[k];

                    // 범위를 벗어나거나 공기청정기가 있으면 확산 불가
                    if (isOutOfBounds(nx, ny) || isNearCleaner(nx, ny)) continue;
                    room[current[0]][current[1]] -= temp;  // 현재 칸의 미세먼지 감소
                    empty[nx][ny] += temp;                 // 확산되는 칸에 미세먼지 추가
                }
            }
        }

        // 확산된 미세먼지 반영
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (empty[i][j] > 0) {
                    room[i][j] += empty[i][j];
                }
            }
        }
    }

    // 공기청정기를 작동시키는 메소드
    private static void runCleaner() {
        // 위쪽 공기청정기 순환
        for (int i = airCleaner.front[0] - 2; i > -1; i--) {
            room[i + 1][0] = room[i][0];
        }
        for (int i = 1; i < c; i++) {
            room[0][i - 1] = room[0][i];
        }
        for (int i = 1; i < airCleaner.front[0] + 1; i++) {
            room[i - 1][c - 1] = room[i][c - 1];
        }
        for (int i = c - 2; i > 0; i--) {
            room[airCleaner.front[0]][i + 1] = room[airCleaner.front[0]][i];
        }
        room[airCleaner.front[0]][1] = 0;

        // 아래쪽 공기청정기 순환
        for (int i = airCleaner.rear[0] + 2; i < r; i++) {
            room[i - 1][0] = room[i][0];
        }
        for (int i = 1; i < c; i++) {
            room[r - 1][i - 1] = room[r - 1][i];
        }
        for (int i = r - 2; i > airCleaner.rear[0] - 1; i--) {
            room[i + 1][c - 1] = room[i][c - 1];
        }
        for (int i = c - 2; i > 0; i--) {
            room[airCleaner.rear[0]][i + 1] = room[airCleaner.rear[0]][i];
        }
        room[airCleaner.rear[0]][1] = 0;
    }

    // 좌표가 범위를 벗어났는지 확인하는 메소드
    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || x > r - 1 || y < 0 || y > c - 1;
    }

    // 해당 좌표가 공기청정기 위치인지 확인하는 메소드
    private static boolean isNearCleaner(int x, int y) {
        return (x == airCleaner.front[0] && y == airCleaner.front[1]) ||
            (x == airCleaner.rear[0] && y == airCleaner.rear[1]);
    }

    // 방에 남아있는 미세먼지의 총량을 계산하는 메소드
    private static int sum() {
        int sum = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (room[i][j] != -1) sum += room[i][j];
            }
        }
        return sum;
    }

    // 공기청정기의 위치 정보를 저장하는 클래스
    static class AirCleaner {
        private int[] front;  // 위쪽 공기청정기 좌표
        private int[] rear;   // 아래쪽 공기청정기 좌표

        // 공기청정기 위치를 설정하는 메소드
        public void setCleaner(int x, int y) {
            if (front == null) {
                front = new int[2];
                front[0] = x;
                front[1] = y;
            } else {
                rear = new int[2];
                rear[0] = x;
                rear[1] = y;
            }
        }
    }
}