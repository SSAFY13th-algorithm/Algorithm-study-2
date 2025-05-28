import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * 백준 2307번 - 도로검문
 * 문제 요약:
 * 1번 정점에서 n번 정점까지 가는 최단 경로를 구하고,
 * 그 경로 상의 도로(간선)를 하나씩 차단했을 때
 * 1에서 n까지 가는 최단 경로의 지연 시간이 최대가 되는 값을 구하는 문제
 */
public class boj2307 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 그래프를 표현하기 위한 인접 리스트
    private static List<List<Node>> graph;

    // 최단 거리를 저장할 배열
    private static int[] dist;

    // 최단 경로 복원을 위한 부모 노드 배열 (이전 노드 정보)
    private static int[] parent;

    // n: 정점의 수, m: 간선의 수
    private static int n, m;

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        // 입력값 초기화 및 그래프 구성
        init();

        // 초기 최단 경로 계산 (도로 차단 없는 상태)
        // 0, 0은 차단할 도로가 없음을 의미
        dijkstra(0, 0);

        // 최단 경로 상의 각 도로를 차단했을 때 최대 지연 시간 계산
        // dist[n]은 초기 최단 경로의 거리
        int answer = block(dist[n]);

        // 결과 출력
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 그래프를 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 정점 수와 간선 수 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 정점의 수
        m = Integer.parseInt(st.nextToken());  // 간선의 수

        // 최단 거리 배열과 부모 노드 배열 초기화
        dist = new int[n + 1];  // 1~n까지의 인덱스 사용
        parent = new int[n + 1];  // 경로 복원을 위한 부모 노드 배열

        // 그래프 초기화 (인접 리스트)
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());  // 각 정점마다 인접 리스트 생성
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());  // 시작 정점
            int v = Integer.parseInt(st.nextToken());  // 도착 정점
            int w = Integer.parseInt(st.nextToken());  // 가중치(시간)

            // 양방향 그래프이므로 양쪽에 모두 간선 추가
            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }
    }

    /**
     * 다익스트라 알고리즘으로 1번 정점에서 각 정점까지의 최단 경로 계산
     * @param u 차단할 도로의 시작 정점 (0이면 차단하지 않음)
     * @param v 차단할 도로의 도착 정점 (0이면 차단하지 않음)
     */
    private static void dijkstra(int u, int v) {
        // 비용을 기준으로 오름차순 정렬하는 우선순위 큐
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));

        // 거리 배열 초기화 (무한대로 설정)
        Arrays.fill(dist, (int)5e8);  // 5 * 10^8는 실질적인 무한대 값

        // 시작점(1번 정점)의 거리는 0으로 설정
        dist[1] = 0;
        pq.add(new Node(1, dist[1]));  // 시작점을 우선순위 큐에 추가

        // 다익스트라 알고리즘 수행
        while (!pq.isEmpty()) {
            // 현재 최단 거리가 가장 짧은 노드 선택
            Node current = pq.poll();

            // 이미 처리된 노드면 스킵 (중복 방지)
            if (current.cost > dist[current.dest]) continue;

            // 현재 노드의 인접 노드들 탐색
            for (Node next : graph.get(current.dest)) {
                // 차단된 도로라면 스킵
                // (u,v)나 (v,u) 형태의 도로를 차단
                if ((current.dest == u && next.dest == v) ||
                    (current.dest == v && next.dest == u)) continue;

                // 현재 노드를 거쳐 다음 노드로 가는 것이 더 짧은 경우
                if (dist[current.dest] + next.cost < dist[next.dest]) {
                    // 최단 거리 갱신
                    dist[next.dest] = dist[current.dest] + next.cost;
                    // 경로 복원을 위해 부모 노드 정보 저장
                    parent[next.dest] = current.dest;
                    // 우선순위 큐에 갱신된 정보 추가
                    pq.add(new Node(next.dest, dist[next.dest]));
                }
            }
        }
    }

    /**
     * 최단 경로 상의 각 도로를 차단했을 때 최대 지연 시간 계산
     * @param first 초기 최단 경로의 거리
     * @return 최대 지연 시간 (도달할 수 없으면 -1)
     */
    private static int block(int first) {
        // 원본 parent 배열 복사 (dijkstra 호출 시 parent가 변경되므로)
        int[] originalParent = Arrays.copyOf(parent, parent.length);

        // 최단 경로 역추적을 위한 변수
        int u = n;  // 도착점부터 시작
        int v = originalParent[u];  // u의 부모 노드 (경로상 이전 노드)
        int time = 0;  // 최대 지연 시간

        // 시작점(1번 정점)에 도달할 때까지 반복
        while (u != 1) {
            // u와 v 사이의 도로를 차단하고 다시 최단 경로 계산
            dijkstra(u, v);

            // n번 정점에 도달할 수 없는 경우
            if (dist[n] == (int)5e8) return -1;

            // 지연 시간 갱신 (현재 최단 경로 - 초기 최단 경로)
            time = Math.max(time, dist[n] - first);

            // 다음 도로로 이동 (경로를 거슬러 올라감)
            u = v;
            v = originalParent[u];
        }

        return time;  // 최대 지연 시간 반환
    }

    /**
     * 그래프의 노드를 표현하는 클래스
     */
    static class Node {
        int dest;  // 도착 정점
        int cost;  // 간선의 가중치(시간)

        // 생성자
        Node(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}