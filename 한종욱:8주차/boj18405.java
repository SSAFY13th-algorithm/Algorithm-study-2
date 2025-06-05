import java.io.*;
import java.util.*;

public class boj18405 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 상하좌우 이동을 위한 방향 배열
    private static final int[] dx = {1, 0, -1, 0};  // 아래, 오른쪽, 위, 왼쪽
    private static final int[] dy = {0, 1, 0, -1};  // 아래, 오른쪽, 위, 왼쪽

    // 바이러스 정보를 저장할 리스트 (x좌표, y좌표, 바이러스 번호)
    private static List<int[]> virus;

    // 시험관 맵 정보를 저장할 2차원 배열
    private static int[][] map;

    // 문제에서 주어지는 입력값
    private static int n;  // 시험관 크기 (N x N)
    private static int k;  // 바이러스 종류
    private static int s;  // 경과 시간
    private static int x;  // 확인할 위치 x좌표
    private static int y;  // 확인할 위치 y좌표

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // BFS로 바이러스 확산 시뮬레이션
        BFS();

        // 결과 출력 (x, y 위치의 바이러스 종류)
        bw.write(map[x][y] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받아 초기 설정을 하는 함수
     */
    private static void init() throws IOException {
        // 시험관 크기와 바이러스 종류 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 맵과 바이러스 리스트 초기화
        map = new int[n][n];
        virus = new ArrayList<>();

        // 시험관 정보 입력 받기
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                // 바이러스가 있는 위치라면 리스트에 추가
                if (map[i][j] != 0) {
                    virus.add(new int[]{i, j, map[i][j]});
                }
            }
        }

        // 바이러스 번호 기준으로 오름차순 정렬 (번호가 낮은 바이러스부터 증식해야 함)
        virus.sort((o1, o2) -> o1[2] - o2[2]);

        // 시간과 확인할 위치 입력 받기
        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());  // 경과 시간
        x = Integer.parseInt(st.nextToken()) - 1;  // 행 번호 (0부터 시작하도록 -1)
        y = Integer.parseInt(st.nextToken()) - 1;  // 열 번호 (0부터 시작하도록 -1)
    }

    /**
     * BFS를 이용해 바이러스 확산을 시뮬레이션하는 함수
     */
    private static void BFS() {
        Queue<int[]> q = new ArrayDeque<>();  // BFS를 위한 큐

        // 초기 바이러스 정보를 큐에 추가 (x좌표, y좌표, 바이러스 번호, 현재 시간)
        for (int[] element : virus) {
            q.add(new int[]{element[0], element[1], element[2], 0});
        }

        // BFS 시작
        while (!q.isEmpty()) {
            int[] current = q.poll();  // 현재 처리할 바이러스 정보

            // 목표 시간에 도달했으면 종료
            if (current[3] == s) return;

            // 상하좌우 네 방향으로 바이러스 확산 시도
            for (int i = 0; i < 4; i++) {
                int nx = current[0] + dx[i];  // 새로운 x좌표
                int ny = current[1] + dy[i];  // 새로운 y좌표

                // 범위를 벗어나거나 이미 바이러스가 있는 경우 건너뛰기
                if (OOB(nx, ny) || map[nx][ny] != 0) continue;

                // 바이러스 확산
                map[nx][ny] = current[2];  // 바이러스 번호로 맵 업데이트

                // 다음 확산을 위해 큐에 추가 (시간 1 증가)
                q.add(new int[]{nx, ny, current[2], current[3] + 1});
            }
        }
    }

    /**
     * 주어진 좌표가 맵 범위를 벗어나는지 체크하는 함수
     * @param nx x좌표
     * @param ny y좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > n - 1;
    }
}