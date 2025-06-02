import java.io.*;
import java.util.*;

public class boj13905 {
    // 입출력 스트림과 상수, 자료구조 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int INF = 1000005; // 충분히 큰 값 설정 (최대 가중치보다 큰 값)
    private static final StringBuilder sb = new StringBuilder();
    private static PriorityQueue<Integer> pq; // 사용되지 않는 변수
    private static List<Node>[] graph; // 그래프 인접 리스트
    private static int[] value; // 각 노드까지의 최대 중량(비용)을 저장할 배열
    private static int n, m, s, e; // n: 노드 수, m: 간선 수, s: 시작점, e: 도착점

    public static void main(String[] args) throws IOException {
        init(); // 그래프 초기화 및 입력 받기
        dijkstra(s); // 시작점에서 다익스트라 알고리즘 실행
        value[s] = 0; // 시작점의 중량을 0으로 설정 (이 부분은 이미 다익스트라에서 처리됨)
        bw.write(value[e] + "\n"); // 도착점까지의 최대 중량 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 그래프 초기화 및 입력 처리 함수
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 노드 수 입력
        m = Integer.parseInt(st.nextToken()); // 간선 수 입력

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken()); // 시작점 입력
        e = Integer.parseInt(st.nextToken()); // 도착점 입력

        // 그래프와 값 배열 초기화
        graph = new List[n + 1];
        value = new int[n + 1];

        // 그래프의 각 노드에 인접 리스트 생성
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()); // 출발 노드
            int v = Integer.parseInt(st.nextToken()); // 도착 노드
            int w = Integer.parseInt(st.nextToken()); // 간선의 가중치(중량)

            // 양방향 그래프이므로 양쪽에 간선 추가
            graph[u].add(new Node(v, w));
            graph[v].add(new Node(u, w));
        }
    }

    // 변형된 다익스트라 알고리즘 - 최대 중량 경로를 찾는 함수
    private static void dijkstra(int start) {
        // 우선순위 큐를 중량이 큰 것이 우선순위가 높도록 구현 (최대 중량 경로를 찾기 위함)
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.cost, o1.cost));

        // 모든 노드의 초기 중량을 0으로 설정
        Arrays.fill(value, 0);

        // 시작점의 중량을 INF로 설정
        value[start] = INF;

        // 시작점을 우선순위 큐에 추가
        pq.add(new Node(start, value[start]));

        while (!pq.isEmpty()) {
            Node current = pq.poll(); // 현재 중량이 가장 큰 노드를 꺼냄

            // 현재 노드의 중량이 이미 알려진 중량보다 작으면 무시
            if (current.cost < value[current.dest]) continue;

            // 현재 노드와 연결된 모든 노드 탐색
            for (Node next : graph[current.dest]) {
                // 현재 노드까지의 중량과 다음 간선의 중량 중 작은 값이 경로의 최대 중량
                int nCost = Math.min(current.cost, next.cost);

                // 새로운 경로의 중량이 기존 중량보다 크면 갱신
                if (nCost > value[next.dest]) {
                    value[next.dest] = nCost;
                    pq.add(new Node(next.dest, value[next.dest]));
                }
            }
        }
    }

    // 노드 클래스 - 목적지 노드와 중량(비용) 정보 저장
    static class Node {
        int dest; // 목적지 노드 번호
        int cost; // 간선의 중량(비용)

        Node(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}