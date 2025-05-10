import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj16929 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 4방향 탐색을 위한 방향 배열 (우,하,좌,상)
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    // 결과 문자열을 만들기 위한 StringBuilder
    private static final StringBuilder sb = new StringBuilder();

    // 게임 보드와 방문 체크 배열
    private static char[][] map;          // 게임 보드 배열
    private static boolean[][] visited;    // 방문 체크 배열
    private static int n, m;              // 보드의 크기 (n X m)

    public static void main(String[] args) throws IOException {
        setting();    // 입력 처리 및 초기 세팅

        // 모든 좌표를 시작점으로 사이클 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                visited = new boolean[n][m];   // 각 시작점마다 방문 배열 초기화
                // 아직 사이클을 찾지 못했고(sb.length() == 0), 현재 위치에서 사이클 발견시
                if (sb.length() == 0 && dfs(i, j, i, j, 0)) {
                    sb.append("Yes");
                    break;
                }
            }
        }

        // 사이클을 찾지 못한 경우
        if (sb.length() == 0) {
            sb.append("No");
        }

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 및 초기 세팅을 위한 메소드
    private static void setting() throws IOException{
        // 보드의 크기 입력 받기
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);

        // 게임 보드 초기화
        map = new char[n][m];

        // 게임 보드 정보 입력 받기
        for (int i = 0; i < n; i++) {
            input = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                map[i][j] = input[j].charAt(0);
            }
        }
    }

    // DFS로 사이클 찾기
    // x, y: 현재 위치
    // startX, startY: 시작 위치
    // cnt: 현재까지의 이동 횟수
    private static boolean dfs(int x, int y, int startX, int startY, int cnt) {
        visited[x][y] = true;  // 현재 위치 방문 체크

        // 4방향 탐색
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 범위를 벗어나거나 다른 색상인 경우 스킵
            if (OOB(nx, ny) || map[nx][ny] != map[x][y]) continue;

            // 시작점으로 돌아왔고, 이동 횟수가 4 이상인 경우 (사이클 발견)
            if (nx == startX && ny == startY && cnt >= 3) {
                return true;
            }

            // 아직 방문하지 않은 위치라면 탐색 계속
            if (!visited[nx][ny]) {
                if (dfs(nx, ny, startX, startY, cnt + 1)) {
                    return true;
                }
            }
        }
        return false;   // 사이클을 찾지 못한 경우
    }

    // 주어진 좌표가 보드 범위를 벗어나는지 체크
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > m - 1;
    }
}