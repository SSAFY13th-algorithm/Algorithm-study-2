import java.util.*;
import java.io.*;

public class boj16973 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 4방향 이동을 위한 배열 (우, 하, 좌, 상)
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    // 맵의 크기(n,m), 직사각형의 크기(h,w)
    private static int n, m, h, w;
    // 시작점과 도착점의 좌표
    private static int[] start = new int[2];
    private static int[] end = new int[2];
    // 맵 정보와 방문 여부를 저장하는 배열
    private static int[][] map;
    private static boolean[][] visited;

    public static void main(String[] args) throws IOException{
        init();  // 입력 처리 및 초기화
        bfs(start[0], start[1]);  // BFS 탐색 수행

        // 도착점에 도달할 수 없으면 -1, 가능하면 최단 거리 출력
        if (map[end[0]][end[1]] == 0) {
            sb.append(-1);
        } else {
            sb.append(map[end[0]][end[1]]);
        }

        // 결과 출력 및 버퍼 정리
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 초기 상태를 설정하는 함수
     * 시간복잡도: O(N * M)
     */
    private static void init() throws IOException {
        // 맵의 크기 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 맵과 방문 배열 초기화
        map = new int[n][m];
        visited = new boolean[n][m];

        // 맵 정보 입력 (1은 -1로 변환하여 장애물 표시)
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) map[i][j] = -1;
            }
        }

        // 직사각형의 크기와 시작점, 도착점 입력
        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        start[0] = Integer.parseInt(st.nextToken()) - 1;
        start[1] = Integer.parseInt(st.nextToken()) - 1;
        end[0] = Integer.parseInt(st.nextToken()) - 1;
        end[1] = Integer.parseInt(st.nextToken()) - 1;
    }

    /**
     * BFS로 최단 경로를 찾는 함수
     * 시간복잡도: O(N * M * (h + w))
     */
    private static void bfs(int x, int y) {
        Queue<int[]> q = new ArrayDeque<>();
        visited[x][y] = true;
        q.add(new int[]{x, y});

        while (!q.isEmpty()) {
            int[] current = q.poll();

            // 도착점에 도달한 경우 종료
            if (current[0] == end[0] && current[1] == end[1]) return;

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nx = current[0] + dx[i];
                int ny = current[1] + dy[i];

                // 맵을 벗어나거나, 장애물이 있거나, 이미 방문한 경우 스킵
                if (OOB(nx, ny) || isBlocked(nx, ny) || visited[nx][ny]) continue;

                visited[nx][ny] = true;
                map[nx][ny] = map[current[0]][current[1]] + 1;  // 이동 거리 갱신
                q.add(new int[]{nx, ny});
            }
        }
    }

    /**
     * 직사각형이 장애물과 겹치는지 확인하는 함수
     * 시간복잡도: O(h + w)
     */
    private static boolean isBlocked(int nx, int ny) {
        // 직사각형의 세로 변에 장애물이 있는지 확인
        for (int i = nx; i < nx+h; i++) {
            if (map[i][ny] == -1 || map[i][ny+w-1] == -1) return true;
        }

        // 직사각형의 가로 변에 장애물이 있는지 확인
        for (int i = ny; i < ny+w; i++) {
            if (map[nx][i] == -1 || map[nx+h-1][i] == -1) return true;
        }

        return false;
    }

    /**
     * 직사각형이 맵을 벗어나는지 확인하는 함수
     * 시간복잡도: O(1)
     */
    private static boolean OOB(int nx, int ny) {
        // 직사각형의 네 꼭짓점이 맵 안에 있는지 확인
        int nx2 = nx;
        int ny2 = ny + w;

        int nx3 = nx + h;
        int ny3 = ny;

        int nx4 = nx + h;
        int ny4 = ny + w;

        if (nx < 0 || ny < 0) return true;
        if (nx2 < 0 || ny2 > m) return true;
        if (nx3 > n || ny3 < 0) return true;
        if (nx4 > n || ny4 > m) return true;

        return false;
    }
}