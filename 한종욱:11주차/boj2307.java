import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 백준 2307번 - 도로검문
 * 문제 요약: 도로망에서 하나의 도로를 차단했을 때 최단 경로가 얼마나 증가하는지 계산하는 문제
 * 1번에서 n번 정점으로 가는 최단 경로를 구하고, 그 경로 상의 각 도로를 하나씩 차단하며
 * 지연 시간이 최대가 되는 경우를 찾는다.
 */
public class boj2307 {
    // 입출력을 위한 객체들 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 그래프 정보를 저장할 인접 리스트
    private static List<List<Node>> graph;

    // 최단 거리를 저장할 배열
    private static int[] dist;

    // 최단 경로에서 이전 정점을 저장할 배열 (경로 복원용)
    private static int[] parent;

    // n: 정점 수, m: 간선 수
    private static int n, m;

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화

        // 초기 상태에서 최단 경로 계산 (차단 도로 없음)
        dijkstra(0, 0);

        // 초기 최단 경로를 기준으로 도로 차단 효과 계산
        int answer = block(dist[n]);

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
        n = Integer.parseInt(st.nextToken());  // 정점 수
        m = Integer.parseInt(st.nextToken());  // 간선 수

        // 배열 초기화
        dist = new int[n + 1];
        parent = new int[n + 1];

        // 그래프 초기화 (인접 리스트)
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());  // 시작 정점
            int v = Integer.parseInt(st.nextToken());  // 도착 정점
            int w = Integer.parseInt(st.nextToken());  // 가중치(시간)

            // 양방향 도로이므로 양쪽에 모두 추가
            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }
    }

    /**
     * 다익스트라 알고리즘으로 최단 경로 계산하는 메소드
     * @param u 차단할 도로의 시작점 (0이면 차단 도로 없음)
     * @param v 차단할 도로의 끝점 (0이면 차단 도로 없음)
     */
    private static void dijkstra(int u, int v) {
        // 최소 힙 기반 우선순위 큐 (비용이 작은 노드 먼저 처리)
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));

        // 모든 거리를 무한대로 초기화 (여기서는 5e8)
        Arrays.fill(dist, (int)5e8);

        // 시작점 1의 거리는 0으로 설정
        dist[1] = 0;
        pq.add(new Node(1, dist[1]));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 이미 처리된 노드라면 스킵
            if (current.cost > dist[current.dest]) continue;

            // 인접 노드 순회
            for (Node next : graph.get(current.dest)) {
                // 차단된 도로라면 스킵
                if ((current.dest == u && next.dest == v) ||
                    (current.dest == v && next.dest == u)) continue;

                // 더 짧은 경로를 발견했다면 갱신
                if (dist[current.dest] + next.cost < dist[next.dest]) {
                    dist[next.dest] = dist[current.dest] + next.cost;
                    parent[next.dest] = current.dest;  // 경로 복원을 위해 부모 노드 저장
                    pq.add(new Node(next.dest, dist[next.dest]));
                }
            }
        }
    }

    /**
     * 최단 경로상의 각 도로를 차단했을 때 지연 시간 계산
     * @param first 초기 최단 경로 거리
     * @return 최대 지연 시간 (도로 차단이 불가능하면 -1)
     */
    private static int block(int first) {
        // 최단 경로를 역순으로 따라가며 각 도로를 차단해보기
        int u = n;  // 도착지에서 시작
        int v = parent[u];  // 도착지 직전 노드
        int time = 0;  // 최대 지연 시간

        // 시작점에 도달할 때까지 반복
        while (u != 1) {
            // u와 v 사이의 도로를 차단하고 다시 다익스트라 실행
            dijkstra(u, v);

            // n까지 도달할 수 없다면 (무한대라면) -1 반환
            if (dist[n] == (int)5e8) return -1;

            // 지연 시간 갱신 (현재 경로 - 초기 최단 경로)
            time = Math.max(time, dist[n] - first);

            // 경로를 따라 이전 노드로 이동
            u = v;
            v = parent[u];
        }

        return time;  // 최대 지연 시간 반환
    }

    /**
     * 그래프의 노드를 표현하는 내부 클래스
     */
    static class Node {
        int dest;  // 도착 정점
        int cost;  // 가중치(시간)

        Node(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}