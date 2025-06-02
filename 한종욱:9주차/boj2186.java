import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class boj2186 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 상하좌우 이동을 위한 방향 배열
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    // BFS를 위한 큐
    private static Queue<int[]> q;

    // 보드 정보 저장
    private static char[][] map;

    // 동적 프로그래밍을 위한 3차원 배열
    // dp[i][j][l]: (i,j) 위치에서 target의 l번째 문자에 도달하는 경우의 수
    private static int[][][] dp;

    // 목표 문자열
    private static char[] target;

    // 보드 크기와 점프 거리
    private static int n, m, k;

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정
        BFS();  // BFS로 경우의 수 계산

        // 최종 결과 계산 (시작 문자에 도달하는 모든 경우의 수 합산)
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                answer += dp[i][j][0];
            }
        }

        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 보드 행 크기
        m = Integer.parseInt(st.nextToken());  // 보드 열 크기
        k = Integer.parseInt(st.nextToken());  // 최대 점프 거리

        map = new char[n][m];  // 보드 배열
        dp = new int[n][m][101];  // DP 배열 (문자열 최대 길이가 100이므로)

        // 보드 정보 입력
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        // 목표 문자열 입력
        target = br.readLine().toCharArray();

        q = new ArrayDeque<>();  // BFS 큐 초기화

        // 목표 문자열의 마지막 문자와 일치하는 모든 위치를 큐에 추가
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == target[target.length - 1]) {
                    q.add(new int[]{i, j, target.length - 1});
                    dp[i][j][target.length - 1] = 1;  // 마지막 문자 위치에서 시작하는 경우의 수는 1
                }
            }
        }
    }

    // BFS로 가능한 경로의 수를 계산하는 메소드
    private static void BFS() {
        while (!q.isEmpty()) {
            int[] current = q.poll();
            // 이미 첫 번째 문자에 도달했으면 더 이상 진행하지 않음
            if (current[2] == 0) continue;

            // 네 방향으로 탐색
            for (int i = 0; i < 4; i++) {
                // 1부터 k까지의 거리만큼 점프
                for (int j = 1; j <= k; j++) {
                    int nx = current[0] + dx[i] * j;
                    int ny = current[1] + dy[i] * j;

                    // 경계를 벗어나거나 이전 문자와 일치하지 않으면 스킵
                    if (OOB(nx, ny) || map[nx][ny] != target[current[2] - 1]) continue;

                    // 현재 위치에서 다음 위치로 가는 경우의 수 누적
                    dp[nx][ny][current[2] - 1] += dp[current[0]][current[1]][current[2]];

                    // 이전에 방문하지 않았던 상태라면 큐에 추가
                    if (dp[nx][ny][current[2] - 1] == dp[current[0]][current[1]][current[2]]) {
                        q.add(new int[]{nx, ny, current[2] - 1});
                    }
                }
            }
        }
    }

    // 배열 범위를 벗어났는지 확인하는 메소드
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > m - 1;
    }
}