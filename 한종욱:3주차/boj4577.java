import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class boj4577 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 상하좌우 이동을 위한 방향 배열
    private static final int[] dx = {1, -1, 0, 0};  // 하, 상, 우, 좌
    private static final int[] dy = {0, 0, 1, -1};

    // 목표지점(+)들의 좌표를 저장하는 리스트
    private static List<int[]> spots;
    // 게임 맵
    private static char[][] map;
    // 캐릭터의 현재 위치
    private static int[] hero = new int[2];
    // 이동 명령어들
    private static char[] commands;
    // 맵의 크기
    private static int r, c;

    public static void main(String[] args) throws IOException {
        // 테스트 케이스 반복
        for (int i = 1; ; i++) {
            String[] input = br.readLine().split(" ");
            r = Integer.parseInt(input[0]);
            c = Integer.parseInt(input[1]);

            // 0 0이 입력되면 종료
            if (r == 0 && c == 0) break;

            // 게임 맵과 명령어 입력 받기
            setting();
            sb.append(String.format("Game %d: ", i));

            // 게임 실행 및 결과 저장
            if (play()) {
                sb.append("complete\n");
            } else {
                sb.append("incomplete\n");
            }

            // 최종 맵 상태 출력
            print();
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 게임 맵과 초기 상태 설정
    private static void setting() throws IOException {
        map = new char[r][c];
        spots = new ArrayList<>();

        // 맵 정보 입력 받기
        for (int i = 0; i < r; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < c; j++) {
                map[i][j] = input[j];
                // 캐릭터 위치 저장
                if (map[i][j] == 'w' || map[i][j] == 'W') {
                    hero[0] = i;
                    hero[1] = j;
                }
                // 목표지점 위치 저장
                if (map[i][j] == '+' || map[i][j] == 'W' || map[i][j] == 'B') {
                    spots.add(new int[]{i, j});
                }
            }
        }
        // 이동 명령어 입력 받기
        commands = br.readLine().toCharArray();
    }

    // 게임 실행
    private static boolean play() {
        for (char command : commands) {
            move(getDirection(command));
            if (finish()) {  // 모든 박스가 목표지점에 도달했는지 확인
                return true;
            }
        }
        return false;
    }

    // 명령어에 따른 방향 반환
    private static int getDirection(char c) {
        if (c == 'U') return 1;  // 상
        if (c == 'D') return 0;  // 하
        if (c == 'R') return 2;  // 우
        return 3;  // 좌
    }

    // 캐릭터 이동 처리
    private static void move(int dir) {
        int nx = hero[0] + dx[dir];
        int ny = hero[1] + dy[dir];

        // 이동 불가능한 경우 리턴
        if (OOB(nx, ny) || blocked(nx, ny, dir)) return;

        // 박스를 밀어야 하는 경우
        if (map[nx][ny] == 'b' || map[nx][ny] == 'B') {
            int nxB = nx + dx[dir];
            int nyB = ny + dy[dir];
            map[nxB][nyB] = isSpot(nxB, nyB) ? 'B' : 'b';
        }

        // 캐릭터 이동 처리
        map[nx][ny] = isSpot(nx, ny) ? 'W' : 'w';
        map[hero[0]][hero[1]] = isSpot(hero[0], hero[1]) ? '+' : '.';
        hero[0] = nx;
        hero[1] = ny;
    }

    // 맵 범위 체크
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > r - 1 || ny < 0 || ny > c - 1;
    }

    // 이동 가능 여부 체크
    private static boolean blocked(int nx, int ny, int dir) {
        if (map[nx][ny] == '#') return true;

        if (map[nx][ny] == 'b' || map[nx][ny] == 'B') {
            int nxB = nx + dx[dir];
            int nyB = ny + dy[dir];
            return OOB(nxB, nyB) || map[nxB][nyB] == '#' ||
                map[nxB][nyB] == 'b' || map[nxB][nyB] == 'B';
        }
        return false;
    }

    // 목표지점 여부 체크
    private static boolean isSpot(int x, int y) {
        for (int[] spot : spots) {
            if (spot[0] == x && spot[1] == y) return true;
        }
        return false;
    }

    // 게임 클리어 여부 체크
    private static boolean finish() {
        for (int[] spot : spots) {
            char c = map[spot[0]][spot[1]];
            if (c != 'B' && c != 'b') return false;
        }
        return true;
    }

    // 최종 맵 상태 출력
    private static void print() {
        // 목표지점 표시 업데이트
        for (int[] spot : spots) {
            char c = map[spot[0]][spot[1]];
            if (c == 'b') map[spot[0]][spot[1]] = 'B';
            else if (c == 'w') map[spot[0]][spot[1]] = 'W';
            else if (c == '.') map[spot[0]][spot[1]] = '+';
        }

        // 맵 출력
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }
    }
}