import java.io.*;
import java.util.*;

public class boj16118 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 그래프를 표현하는 인접 리스트
    private static List<Node>[] graph;

    // 여우와 늑대의 최단 거리를 저장하는 배열
    private static long[] distFox;       // 여우의 각 노드까지의 최단 거리
    private static long[][] distWolf;    // 늑대의 각 노드까지의 최단 거리 [노드번호][상태]
    // 상태: 0 - 빠른 상태로 도착, 1 - 느린 상태로 도착

    // n: 그래프의 정점 수, m: 그래프의 간선 수
    private static int n, m;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // 여우의 최단 경로 계산 (시작점: 1번 노드)
        dijkstraFox(1);

        // 늑대의 최단 경로 계산 (시작점: 1번 노드)
        dijkstraWolf(1);

        // 여우가 늑대보다 먼저 도착할 수 있는 노드 수 계산
        int answer = 0;
        for (int i = 2; i <= n; i++) {
            long fox = distFox[i];                     // 여우가 i번 노드에 도착하는 최단 시간
            long wolf = Math.min(distWolf[i][0], distWolf[i][1]); // 늑대가 i번 노드에 도착하는 최단 시간 (두 상태 중 더 빠른 시간)

            // 여우가 늑대보다 빠르게 도착할 수 있으면 카운트 증가
            if (fox < wolf) answer++;
        }

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
        n = Integer.parseInt(st.nextToken()); // 정점의 수
        m = Integer.parseInt(st.nextToken()); // 간선의 수

        // 그래프와 최단 거리 배열 초기화
        graph = new List[n + 1];
        distFox = new long[n + 1];
        distWolf = new long[n + 1][2]; // [노드번호][상태] - 상태 0: 빠른 속도, 상태 1: 느린 속도

        // 그래프의 각 노드별 인접 리스트 생성 및 거리 배열 초기화
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
            Arrays.fill(distWolf[i], (long)2e10); // 늑대 거리 배열을 매우 큰 값으로
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()); // 시작 정점
            int v = Integer.parseInt(st.nextToken()); // 도착 정점
            int w = Integer.parseInt(st.nextToken()); // 가중치(거리)

            // 양방향 그래프 생성 (모든 간선 가중치를 2배로 저장)
            // 이는 늑대가 느린 상태일 때 정확히 0.5배 속도로 이동할 수 있게 하기 위함
            graph[u].add(new Node(v, w * 2));
            graph[v].add(new Node(u, w * 2));
        }
    }

    /**
     * 여우의 최단 경로를 계산하는 다익스트라 알고리즘
     * @param start 시작 노드
     */
    private static void dijkstraFox(int start) {
        // 우선순위 큐를 이용한 다익스트라 알고리즘 구현
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));

        // 최단 거리 배열 초기화
        Arrays.fill(distFox, (long)2e10);
        distFox[start] = 0;

        // 시작 노드 큐에 삽입
        pq.add(new Node(start, distFox[start]));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 이미 처리된 노드면 스킵
            if (current.cost > distFox[current.dest]) continue;

            // 현재 노드와 연결된 모든 노드에 대해
            for (Node next : graph[current.dest]) {
                int nDest = next.dest;
                long nCost = next.cost;

                // 더 짧은 경로를 발견하면 최단 거리 갱신 및 큐에 추가
                if (distFox[current.dest] + nCost < distFox[nDest]) {
                    distFox[nDest] = distFox[current.dest] + nCost;
                    pq.add(new Node(nDest, distFox[nDest]));
                }
            }
        }
    }

    /**
     * 늑대의 최단 경로를 계산하는 다익스트라 알고리즘
     * 늑대는 빠른 상태와 느린 상태를 번갈아가며 이동
     * @param start 시작 노드
     */
    private static void dijkstraWolf(int start) {
        // 우선순위 큐를 이용한 다익스트라 알고리즘 구현
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));

        // 늑대는 빠른 상태(flag=1)로 시작
        distWolf[start][0] = 0;
        pq.add(new Node(start, distWolf[start][0], 1)); // flag 1: 빠른 상태

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 이미 처리된 노드면 스킵
            if (current.flag == 1 && current.cost > distWolf[current.dest][0]) continue;
            if (current.flag == -1 && current.cost > distWolf[current.dest][1]) continue;

            // 현재 노드와 연결된 모든 노드에 대해
            for (Node next : graph[current.dest]) {
                int nDest = next.dest;
                long nCost;

                // 현재 상태에 따라 다음 간선의 비용 계산
                // flag=1 (빠른 상태)일 때는 2배 빠름 (간선 비용/2)
                // flag=-1 (느린 상태)일 때는 0.5배 느림 (간선 비용*2)
                if (current.flag == 1) nCost = next.cost / 2;
                else nCost = next.cost * 2;

                // 다음 상태는 현재 상태의 반대
                int nFlag = current.flag * -1;

                // 상태에 따라 최단 거리 갱신
                // nFlag=1 (다음에 빠른 상태)이면 distWolf[nDest][0] 갱신
                if (nFlag == 1 && distWolf[current.dest][1] + nCost < distWolf[nDest][0]) {
                    distWolf[nDest][0] = distWolf[current.dest][1] + nCost;
                    pq.add(new Node(nDest, distWolf[nDest][0], nFlag));
                }

                // nFlag=-1 (다음에 느린 상태)이면 distWolf[nDest][1] 갱신
                if (nFlag == -1 && distWolf[current.dest][0] + nCost < distWolf[nDest][1]) {
                    distWolf[nDest][1] = distWolf[current.dest][0] + nCost;
                    pq.add(new Node(nDest, distWolf[nDest][1], nFlag));
                }
            }
        }
    }

    /**
     * 그래프의 노드 정보를 저장하는 클래스
     */
    static class Node {
        int dest;   // 도착 노드
        long cost;  // 간선 비용
        int flag;   // 늑대의 상태 (1: 빠른 상태, -1: 느린 상태, 0: 여우)

        // 여우용 생성자
        Node(int dest, long cost) {
            this.dest = dest;
            this.cost = cost;
            this.flag = 0;
        }

        // 늑대용 생성자
        public Node(int dest, long cost, int flag) {
            this.dest = dest;
            this.cost = cost;
            this.flag = flag;
        }
    }
}