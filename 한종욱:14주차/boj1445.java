import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class boj1445 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 무한대 값 정의 (최대 가능한 값보다 큼)
    private static final int INF = 3000;

    // 상하좌우 이동을 위한 방향 배열 (동, 남, 서, 북)
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};

    // 숲 지도를 저장하는 2차원 배열
    // 'S': 시작점, 'F': 도착점, 'g': 쓰레기, '.': 빈 공간
    private static char[][] map;

    // 최단 경로 정보를 저장하는 3차원 배열
    // dist[x][y][0]: (x,y)까지 지나온 쓰레기 개수
    // dist[x][y][1]: (x,y)까지 지나온 인접한 쓰레기 개수
    private static int[][][] dist;

    // 시작점과 도착점 좌표
    private static int[] start, end;

    // 숲의 크기
    private static int n, m;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기화
        init();

        // 다익스트라 알고리즘으로 최단 경로 찾기
        dijkstra();

        // 결과 출력 (지나간 쓰레기 개수와 인접한 쓰레기 개수)
        sb.append(dist[end[0]][end[1]][0]).append(" ").append(dist[end[0]][end[1]][1]);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 숲의 크기 입력 (행과 열)
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 배열 초기화
        map = new char[n][m];
        dist = new int[n][m][2];
        start = new int[2];
        end = new int[2];

        // 숲의 지도 정보 입력
        for (int i = 0; i < n; i++) {
            String input = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = input.charAt(j);

                // 시작점 좌표 저장
                if (map[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                }
                // 도착점 좌표 저장
                else if (map[i][j] == 'F') {
                    end[0] = i;
                    end[1] = j;
                }

                // 모든 위치의 초기 거리를 무한대로 설정
                dist[i][j][0] = INF;
                dist[i][j][1] = INF;
            }
        }
    }

    /**
     * 다익스트라 알고리즘을 사용하여 최단 경로를 찾는 메소드
     * 우선 순위:
     * 1. 지나가는 쓰레기 개수가 적은 경로
     * 2. 인접한 쓰레기 개수가 적은 경로
     */
    private static void dijkstra() {
        // 우선순위 큐 정의 (최소 힙) - 배열의 의미:
        // int[0]: x좌표, int[1]: y좌표, int[2]: 지나간 쓰레기 개수, int[3]: 인접한 쓰레기 개수
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            // 지나간 쓰레기 개수가 같으면 인접한 쓰레기 개수로 비교
            if (o1[2] == o2[2]) return Integer.compare(o1[3], o2[3]);
            // 지나간 쓰레기 개수로 비교
            return Integer.compare(o1[2], o2[2]);
        });

        // 시작점 초기화
        dist[start[0]][start[1]][0] = 0; // 시작점에서 지나간 쓰레기 개수
        dist[start[0]][start[1]][1] = 0; // 시작점에서 인접한 쓰레기 개수
        pq.add(new int[]{start[0], start[1], dist[start[0]][start[1]][0], dist[start[0]][start[1]][1]});

        // 다익스트라 알고리즘 수행
        while (!pq.isEmpty()) {
            int[] current = pq.poll();

            // 이미 더 짧은 경로를 찾은 경우 건너뛰기
            if (current[2] > dist[current[0]][current[1]][0]) continue;
            // 쓰레기 개수는 같지만 인접한 쓰레기 개수가 더 많은 경우 건너뛰기
            if (current[2] == dist[current[0]][current[1]][0] && current[3] > dist[current[0]][current[1]][1]) continue;

            // 상하좌우 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nx = current[0] + dx[i];
                int ny = current[1] + dy[i];

                // 범위를 벗어나면 건너뛰기
                if (OOB(nx, ny)) continue;

                // 다음 위치가 쓰레기면 1, 아니면 0
                int onG = map[nx][ny] == 'g' ? 1 : 0;

                // 다음 위치가 빈 공간이면 인접한 쓰레기 개수 계산
                int nearG = map[nx][ny] == '.' ? countNearG(nx, ny) : 0;

                // 더 좋은 경로를 찾은 경우 갱신
                // 1. 지나간 쓰레기 개수가 더 적거나
                // 2. 지나간 쓰레기 개수는 같지만 인접한 쓰레기 개수가 더 적은 경우
                if ((onG + current[2] < dist[nx][ny][0]) ||
                    (onG + current[2] == dist[nx][ny][0] && nearG + current[3] < dist[nx][ny][1])) {
                    // 거리 정보 갱신
                    dist[nx][ny][0] = onG + current[2];
                    dist[nx][ny][1] = nearG + current[3];
                    // 우선순위 큐에 추가
                    pq.add(new int[]{nx, ny, dist[nx][ny][0], dist[nx][ny][1]});
                }
            }
        }
    }

    /**
     * 주어진 좌표가 숲의 범위를 벗어나는지 확인하는 메소드
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > m - 1;
    }

    /**
     * 주어진 위치 주변(상하좌우)에 쓰레기가 있는지 확인하는 메소드
     * @param x x 좌표
     * @param y y 좌표
     * @return 인접한 곳에 쓰레기가 있으면 1, 없으면 0
     */
    private static int countNearG(int x, int y) {
        // 상하좌우 4방향 확인
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 범위를 벗어나면 건너뛰기
            if (OOB(nx, ny)) continue;

            // 인접한 곳에 쓰레기가 있으면 1 반환
            if (map[nx][ny] == 'g') return 1;
        }

        // 인접한 곳에 쓰레기가 없으면 0 반환
        return 0;
    }
}