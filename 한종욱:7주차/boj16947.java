import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class boj16947 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 그래프와 결과 저장을 위한 변수 선언
    private static List<List<Integer>> graph;    // 그래프의 인접 리스트 표현
    private static Set<Integer> cycleNodes;      // 사이클에 포함된 노드들을 저장하는 집합
    private static int[] distance;               // 각 노드에서 사이클까지의 최단 거리
    private static boolean[] visited;            // DFS 및 BFS에서 노드 방문 여부 확인
    private static int n;                        // 노드의 개수

    public static void main(String[] args) throws IOException{
        // 그래프 초기화 및 입력 처리
        init();

        // 1번 노드부터 DFS 탐색 시작하여 사이클 찾기
        List<Integer> temp = new ArrayList<>();  // 현재 DFS 경로를 저장하는 임시 리스트
        DFS(1, -1, temp);                      // 시작 노드 1, 부모 노드 없음(-1)

        // BFS로 사이클에서 각 노드까지의 최단 거리 계산
        BFS();

        // 결과 출력
        for (int i = 1; i <= n; i++) {
            sb.append(distance[i]).append(" ");
        }
        sb.setLength(sb.length() - 1);  // 마지막 공백 제거

        bw.write(sb.toString() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받아 그래프와 필요한 데이터 구조 초기화
     */
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 노드 개수 입력

        // 데이터 구조 초기화
        graph = new ArrayList<>();
        cycleNodes = new TreeSet<>();  // 사이클 노드 정렬을 위해 TreeSet 사용
        distance = new int[n + 1];
        visited = new boolean[n + 1];

        // 그래프의 인접 리스트 초기화 (1-indexed)
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            // 양방향 그래프이므로 양쪽에 추가
            graph.get(node1).add(node2);
            graph.get(node2).add(node1);
        }
    }

    /**
     * DFS를 사용하여 그래프에서 사이클 찾기
     * @param node 현재 방문 중인 노드
     * @param parent 현재 노드의 부모 노드 (직전에 방문한 노드)
     * @param temp 현재까지의 DFS 경로를 저장하는 리스트
     */
    private static void DFS(int node, int parent, List<Integer> temp) {
        visited[node] = true;     // 현재 노드 방문 표시
        temp.add(node);           // 현재 경로에 노드 추가

        // 현재 노드의 모든 인접 노드 확인
        for (int next : graph.get(node)) {
            // 부모 노드는 건너뛰기 (양방향 그래프에서 바로 되돌아가는 것 방지)
            if (next == parent) continue;

            if (visited[next]) {
                // 이미 방문한 노드를 다시 만났다면 사이클 가능성 있음
                int index = temp.indexOf(next);
                if (index != -1) {  // 현재 경로에 있는 노드라면 사이클 발견
                    // 사이클에 포함된 노드들 추가 (index부터 현재까지)
                    for (int i = index; i < temp.size(); i++) {
                        cycleNodes.add(temp.get(i));
                    }
                    return;  // 사이클 찾았으므로 탐색 중단
                }
            } else {
                // 아직 방문하지 않은 노드면 계속 탐색
                DFS(next, node, temp);
                // 사이클을 찾았으면 더 이상 탐색하지 않고 반환
                if (!cycleNodes.isEmpty()) return;
            }
        }

        // 백트래킹: 더 이상 탐색할 곳이 없으면 현재 노드를 경로에서 제거
        temp.remove(temp.size() - 1);
    }

    /**
     * BFS를 사용하여 각 노드에서 사이클까지의 최단 거리 계산
     */
    private static void BFS() {
        Queue<int[]> q = new ArrayDeque<>();
        // visited 배열 초기화 (DFS에서 사용한 후이므로)
        visited = new boolean[n + 1];

        // 사이클에 속한 모든 노드를 시작점으로 큐에 추가
        for (int node : cycleNodes) {
            visited[node] = true;
            distance[node] = 0;  // 사이클 노드의 거리는 0
            q.add(new int[] {node, 0});  // [노드 번호, 거리]
        }

        // BFS 탐색 시작
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int currentNode = current[0];
            int currentDist = current[1];

            // 현재 노드의 모든 인접 노드 확인
            for (int next : graph.get(currentNode)) {
                if (visited[next]) continue;  // 이미 방문한 노드는 건너뛰기

                visited[next] = true;

                // 인접 노드가 사이클에 포함되어 있으면 거리는 그대로
                // (이 부분은 실제로는 실행되지 않을 것임 - 이미 사이클 노드는 visited=true로 표시되어 있음)
                if (cycleNodes.contains(next)) {
                    distance[next] = currentDist;
                    q.add(new int[] {next, currentDist});
                }
                else {
                    // 사이클에 포함되지 않은 노드는 거리 1 증가
                    distance[next] = currentDist + 1;
                    q.add(new int[] {next, currentDist + 1});
                }
            }
        }
    }
}