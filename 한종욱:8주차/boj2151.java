import java.io.*;
import java.util.*;
public class boj2151 {
    // 입출력을 위한 버퍼 리더와 라이터 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 방향 상수 정의
    private static final int VERTICAL = 0;   // 수직 방향(위아래)
    private static final int HORIZONTAL = 1; // 수평 방향(좌우)

    // 출발지와 목적지 좌표를 저장할 리스트
    private static final List<int[]> dest = new ArrayList<>();

    // 맵 정보와 방문 여부를 저장할 배열
    private static char[][] home;        // 집(맵) 정보 배열
    private static boolean[][] visited;  // 방문 여부 배열
    private static int n;                // 맵의 크기

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        // 입력 및 초기화
        init();

        // 다익스트라 알고리즘으로 최단 경로 계산
        // 미러 설치 수는 경로 비용 - 1 (도착지까지 거울로 세기 때문에 -1)
        int answer = dijkstra() - 1;

        // 결과 출력 및 리소스 정리
        bw.write(answer + "");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 맵 크기 입력
        n = Integer.parseInt(br.readLine());

        // 맵과 방문 배열 초기화
        home = new char[n][n];
        visited = new boolean[n][n];

        // 맵 정보 입력 및 출발지/목적지 찾기
        for (int i = 0; i < n; i++) {
            home[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                // '#' 표시가 있는 칸은 출발지와 목적지로 저장
                if (home[i][j] == '#') {
                    dest.add(new int[]{i, j});
                }
            }
        }
    }

    /**
     * 다익스트라 알고리즘으로 최단 경로를 찾는 메소드
     * 레이저가 목적지에 도달하기 위해 필요한 최소 미러 수를 계산
     *
     * @return 필요한 노드 방문 횟수 (미러 수 + 1)
     */
    private static int dijkstra() {
        // 비용이 적은 노드부터 처리하기 위한 우선순위 큐
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);

        // 시작 노드(출발지) 방문 처리 및 큐에 추가 (두 방향 모두 큐에 추가)
        visited[dest.get(0)[0]][dest.get(0)[1]] = true;
        pq.add(new Node(dest.get(0)[0], dest.get(0)[1], 0, VERTICAL));
        pq.add(new Node(dest.get(0)[0], dest.get(0)[1], 0, HORIZONTAL));

        // 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            // 현재 처리할 노드 꺼내기
            Node current = pq.poll();

            // 목적지에 도달한 경우 현재까지의 비용 반환
            if (current.x == dest.get(1)[0] && current.y == dest.get(1)[1]) return current.cost;

            // 현재 방향에 따라 다음 위치 탐색
            if (current.dir == VERTICAL) {
                // 수직 방향인 경우 - 아래쪽 탐색
                for (int i = current.x + 1; i < n; i++) {
                    // 벽을 만나면 더 이상 진행 불가
                    if (home[i][current.y] == '*') break;
                    // 빈 공간이거나 이미 방문한 경우 스킵
                    if (home[i][current.y] == '.' || visited[i][current.y]) continue;
                    // 방문 처리 및 큐에 추가 (방향 전환하여 수평으로)
                    visited[i][current.y] = true;
                    pq.add(new Node(i, current.y, current.cost + 1, HORIZONTAL));
                }

                // 수직 방향인 경우 - 위쪽 탐색
                for (int i = current.x - 1; i >= 0; i--) {
                    // 벽을 만나면 더 이상 진행 불가
                    if (home[i][current.y] == '*') break;
                    // 빈 공간이거나 이미 방문한 경우 스킵
                    if (home[i][current.y] == '.' || visited[i][current.y]) continue;
                    // 방문 처리 및 큐에 추가 (방향 전환하여 수평으로)
                    visited[i][current.y] = true;
                    pq.add(new Node(i, current.y, current.cost + 1, HORIZONTAL));
                }
            } else {
                // 수평 방향인 경우 - 오른쪽 탐색
                for (int i = current.y + 1; i < n; i++) {
                    // 벽을 만나면 더 이상 진행 불가
                    if (home[current.x][i] == '*') break;
                    // 빈 공간이거나 이미 방문한 경우 스킵
                    if (home[current.x][i] == '.' || visited[current.x][i]) continue;
                    // 방문 처리 및 큐에 추가 (방향 전환하여 수직으로)
                    visited[current.x][i] = true;
                    pq.add(new Node(current.x, i, current.cost + 1, VERTICAL));
                }

                // 수평 방향인 경우 - 왼쪽 탐색
                for (int i = current.y - 1; i >= 0; i--) {
                    // 벽을 만나면 더 이상 진행 불가
                    if (home[current.x][i] == '*') break;
                    // 빈 공간이거나 이미 방문한 경우 스킵
                    if (home[current.x][i] == '.' || visited[current.x][i]) continue;
                    // 방문 처리 및 큐에 추가 (방향 전환하여 수직으로)
                    visited[current.x][i] = true;
                    pq.add(new Node(current.x, i, current.cost + 1, VERTICAL));
                }
            }
        }

        // 목적지에 도달할 수 없는 경우
        return 0;
    }

    /**
     * 노드 클래스: 위치, 비용, 방향 정보를 저장
     */
    static class Node {
        int x;      // 행 좌표
        int y;      // 열 좌표
        int cost;   // 설치한 미러 수
        int dir;    // 현재 방향 (VERTICAL 또는 HORIZONTAL)

        /**
         * 노드 생성자
         *
         * @param x 행 좌표
         * @param y 열 좌표
         * @param cost 현재까지의 비용(미러 수)
         * @param dir 현재 방향
         */
        Node(int x, int y, int cost, int dir) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.dir = dir;
        }
    }
}