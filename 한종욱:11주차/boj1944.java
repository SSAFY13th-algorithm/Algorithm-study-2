import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 백준 1944번 - 복제 로봇
 * 문제 요약:
 * N×N 미로에서 로봇이 모든 열쇠(K)를 찾아 수집하는 최소 이동 횟수를 구하는 문제
 * 로봇의 시작 위치(S)와 열쇠 위치(K)가 주어지며, 0은 빈 칸, 1은 벽을 의미
 * MST(최소 신장 트리) 알고리즘을 활용하여 해결
 */
public class boj1944 {
    // 입출력을 위한 객체들 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 4방향 이동을 위한 dx, dy 배열 (동, 남, 서, 북)
    private static final int[] dx = { 1, 0, -1, 0 };
    private static final int[] dy = { 0, 1, 0, -1 };

    // 무한대 값 설정 (충분히 큰 값)
    private static final int INF = (int) 7e6;

    // n: 미로의 크기, m: 열쇠의 개수
    private static int n, m;

    // 미로 정보를 저장할 2차원 배열
    private static char[][] map;

    // 시작 위치(S)와 열쇠 위치(K)를 저장할 리스트
    private static List<int[]> keyPoints = new ArrayList<>();

    // 각 지점 간의 최단 거리를 저장할 2차원 배열
    private static int[][] distances;

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화
        int answer = prim();  // 프림 알고리즘으로 MST 구성

        // 결과 출력
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 변수를 초기화하는 메소드
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 미로의 크기
        m = Integer.parseInt(st.nextToken());  // 열쇠의 개수

        // 미로 배열 초기화
        map = new char[n][n];

        // 미로 정보 입력
        for (int i = 0; i < n; i++) {
            String input = br.readLine();
            for (int j = 0; j < n; j++) {
                map[i][j] = input.charAt(j);

                // 시작 위치(S)와 열쇠 위치(K)를 keyPoints 리스트에 추가
                if (map[i][j] == 'K' || map[i][j] == 'S') {
                    keyPoints.add(new int[] { i, j });
                }
            }
        }

        // 각 지점 간의 거리를 저장할 2차원 배열 초기화
        // m+1은 시작 위치(S)와 m개의 열쇠 위치(K)를 모두 포함
        distances = new int[m + 1][m + 1];
        for (int i = 0; i < m + 1; i++) {
            Arrays.fill(distances[i], INF);  // 모든 거리를A 무한대로 초기화
            distances[i][i] = 0;  // 자기 자신까지의 거리는 0
        }

        // 모든 지점 간의 최단 거리 계산
        calculateAllDistances();
    }

    /**
     * 모든 지점 간의 최단 거리를 BFS로 계산하는 메소드
     */
    private static void calculateAllDistances() {
        // 각 시작 위치(S)와 열쇠 위치(K)에서 다른 모든 지점까지의 거리 계산
        for (int i = 0; i < m + 1; i++) {
            int[] start = keyPoints.get(i);
            BFS(start[0], start[1], i);  // BFS로 최단 거리 계산
        }
    }

    /**
     * BFS로 특정 지점에서 다른 모든 지점까지의 최단 거리를 계산하는 메소드
     * @param startX 시작 지점의 x 좌표
     * @param startY 시작 지점의 y 좌표
     * @param startIdx 시작 지점의 인덱스
     */
    private static void BFS(int startX, int startY, int startIdx) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];

        // 시작 지점을 큐에 추가 [x좌표, y좌표, 거리]
        q.add(new int[] { startX, startY, 0 });
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int x = current[0];
            int y = current[1];
            int dist = current[2];

            // 현재 위치가 시작 위치(S) 또는 열쇠 위치(K)인지 확인
            for (int i = 0; i < m + 1; i++) {
                int[] point = keyPoints.get(i);
                if (x == point[0] && y == point[1]) {
                    // 시작 지점에서 현재 지점까지의 거리 저장
                    distances[startIdx][i] = dist;
                }
            }

            // 4방향 탐색
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                // 미로 범위를 벗어나거나 벽이거나 이미 방문한 지점이면 스킵
                if (OOB(nx, ny) || map[nx][ny] == '1' || visited[nx][ny]) {
                    continue;
                }

                // 방문 표시 후 큐에 추가
                visited[nx][ny] = true;
                q.add(new int[] { nx, ny, dist + 1 });
            }
        }
    }

    /**
     * 좌표가 미로 범위를 벗어나는지 확인하는 메소드
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > n - 1;
    }

    /**
     * 프림 알고리즘으로 MST(최소 신장 트리)를 구성하는 메소드
     * @return MST의 총 가중치 (모든 열쇠를 수집하는 최소 이동 횟수)
     */
    private static int prim() {
        boolean[] visited = new boolean[m + 1];  // 방문 여부 배열
        int[] dist = new int[m + 1];  // 현재 MST에서 각 정점까지의 최소 거리

        // 모든 거리를 무한대로 초기화
        Arrays.fill(dist, INF);
        dist[0] = 0;  // 시작 지점의 거리는 0 (인덱스 0은 시작 위치 S)

        // 우선순위 큐 초기화 (거리가 짧은 순으로 정렬)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1])); // [노드, 비용]
        pq.add(new int[] { 0, 0 });  // 시작 지점 추가 [노드 인덱스, 비용]
        int totalCost = 0;  // MST의 총 가중치

        // 프림 알고리즘 수행
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];  // 현재 노드
            int cost = current[1];  // 현재까지의 비용

            // 이미 방문한 노드면 스킵
            if (visited[node])
                continue;

            // 노드 방문 처리 및 비용 누적
            visited[node] = true;
            totalCost += cost;

            // 현재 노드에서 갈 수 있는 다른 노드들 탐색
            for (int next = 0; next < m + 1; next++) {
                // 방문하지 않았고 현재 알고 있는 거리보다 더 짧은 경로가 있다면 갱신
                if (!visited[next] && distances[node][next] < dist[next]) {
                    dist[next] = distances[node][next];
                    pq.add(new int[] { next, dist[next] });
                }
            }
        }

        // 모든 열쇠를 수집할 수 있는지 확인
        for (int i = 0; i < m + 1; i++) {
            if (!visited[i])
                return -1;  // 방문하지 못한 정점이 있으면 불가능
        }

        return totalCost;  // MST의 총 가중치 반환
    }
}