import java.io.*;
import java.util.StringTokenizer;

public class boj1405 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 4방향 이동을 위한 x, y 좌표 변화량 (동, 서, 남, 북)
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

    // 방문 여부를 저장하는 배열
    private static boolean[][] visited;

    // 각 방향으로 이동할 확률 (%)
    private static int[] moveRate;

    // 이동할 총 횟수
    private static int n;

    // 로봇이 단순하지 않은 경로로 이동할 확률
    private static double answer;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // 시작 위치(14, 14)를 방문 처리
        visited[14][14] = true;

        // DFS 탐색 시작: 시작 좌표(14, 14), 이동 카운트 0, 초기 확률 1
        dfs(14, 14, 0, 1);

        // 결과 출력
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력 데이터를 초기화하는 함수
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 첫 번째 숫자는 총 이동 횟수 n
        n = Integer.parseInt(st.nextToken());

        // 이동 확률 배열 초기화
        moveRate = new int[4];

        // 방문 배열 초기화 (시작 위치를 중앙에 두고 충분한 크기로 설정)
        visited = new boolean[29][29];

        // 동, 서, 남, 북 방향으로의 이동 확률 입력 받기
        for (int i = 0; i < 4; i++) {
            moveRate[i] = Integer.parseInt(st.nextToken());
        }
    }

    /**
     * DFS로 모든 가능한 경로를 탐색하는 함수
     *
     * @param x 현재 x 좌표
     * @param y 현재 y 좌표
     * @param count 현재까지 이동한 횟수
     * @param rate 현재 경로의 확률
     */
    private static void dfs(int x, int y, int count, double rate) {
        // 기저 조건: n번 이동했으면 해당 경로의 확률을 더함
        if (count == n) {
            answer += rate;
            return;
        }

        // 네 방향으로의 이동 시도
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 해당 방향으로 이동할 확률이 0이면 건너뜀
            if (moveRate[i] == 0) continue;

            // 아직 방문하지 않은 위치라면
            if (!visited[nx][ny]) {
                // 방문 처리
                visited[nx][ny] = true;

                // 다음 위치로 이동하며 확률 갱신 (현재 확률 * 해당 방향 이동 확률)
                dfs(nx, ny, count + 1, rate * ((double)moveRate[i]/100));

                // 백트래킹: 방문 기록 원복
                visited[nx][ny] = false;
            }
        }
    }
}