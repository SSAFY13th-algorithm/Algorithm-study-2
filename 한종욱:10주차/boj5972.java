import java.io.*;
import java.util.*;

public class boj5972 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 무한대를 표현하기 위한 상수 값
    private static final int MAX_INT = 50000005;

    // 그래프 표현을 위한 인접 리스트
    private static List<List<Node>> graph;

    // 각 정점까지의 최단 거리를 저장하는 배열
    private static int[] dist;

    // n: 정점의 수, m: 간선의 수
    private static int n, m;

    public static void main(String[] args) throws IOException {
        init();          // 초기 데이터 설정
        dijkstra();      // 다익스트라 알고리즘으로 최단 경로 계산

        // 목적지(n)까지의 최단 거리 출력
        int answer = dist[n];
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 헛간의 개수
        m = Integer.parseInt(st.nextToken());  // 길의 개수

        // 그래프 초기화
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 거리 배열 초기화
        dist = new int[n + 1];
        Arrays.fill(dist, MAX_INT);  // 모든 거리를 무한대로 초기화
        dist[1] = 0;  // 시작점의 거리는 0

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());  // 출발 헛간
            int n2 = Integer.parseInt(st.nextToken());  // 도착 헛간
            int cost = Integer.parseInt(st.nextToken());  // 소의 수(비용)

            // 양방향 그래프 구성
            graph.get(n1).add(new Node(n2, cost));
            graph.get(n2).add(new Node(n1, cost));
        }
    }

    // 다익스트라 알고리즘으로 최단 경로를 계산하는 메소드
    private static void dijkstra() {
        // 우선순위 큐를 이용한 다익스트라 알고리즘
        // 비용이 작은 순서대로 정렬
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));

        // 시작점을 큐에 추가
        pq.add(new Node(1, dist[1]));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 이미 처리된 노드는 스킵
            if (current.cost > dist[current.dest]) continue;

            // 현재 노드와 연결된 모든 노드 검사
            for (Node next : graph.get(current.dest)) {
                int nDest = next.dest;    // 다음 목적지
                int nCost = next.cost;    // 다음 목적지까지의 비용

                // 더 짧은 경로를 발견한 경우 업데이트
                if (dist[current.dest] + nCost < dist[nDest]) {
                    dist[nDest] = dist[current.dest] + nCost;
                    pq.add(new Node(nDest, dist[nDest]));
                }
            }
        }
    }

    // 그래프의 노드 정보를 저장하는 클래스
    static class Node {
        int dest;  // 목적지 노드 번호
        int cost;  // 해당 노드까지의 비용(소의 수)

        Node (int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}