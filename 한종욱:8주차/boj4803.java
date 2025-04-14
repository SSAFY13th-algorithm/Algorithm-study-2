import java.io.*;
import java.util.*;
public class boj4803 {
    // 입출력을 위한 버퍼 리더와 라이터 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 그래프 구조를 저장할 인접 리스트
    private static List<List<Integer>> graph;

    // 노드 방문 여부를 저장할 배열
    private static boolean[] visited;

    // 사이클 존재 여부를 나타내는 플래그
    private static boolean isCycle;

    // n: 노드의 수, m: 간선의 수, answer: 트리의 수
    private static int n, m, answer;

    public static void main(String[] args) throws IOException {
        // 테스트 케이스 번호
        int t = 1;

        // init() 함수가 false를 반환할 때까지 테스트 케이스 반복
        while (init()) {
            // 모든 노드에 대해 방문하지 않은 노드부터 DFS 실행
            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    // 사이클 플래그 초기화
                    isCycle = false;

                    // 연결 요소 발견, 트리 개수 증가
                    answer++;

                    // 시작 노드 방문 처리
                    visited[i] = true;

                    // DFS 실행 (parent = -1로 시작, 루트 노드는 부모가 없음)
                    DFS(i, -1);

                    // 사이클이 발견되면 트리가 아니므로 개수 감소
                    if (isCycle) answer--;
                }
            }

            // 결과 출력을 위한 문자열 구성
            sb.append("Case ").append(t++).append(": ");
            if (answer == 0) sb.append("No trees.");
            else if (answer == 1) sb.append("There is one tree.");
            else sb.append("A forest of ").append(answer).append(" trees.");
            sb.append("\n");
        }

        // 최종 결과 출력 및 리소스 정리
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받아 그래프를 초기화하는 메소드
     * @return 유효한 테스트 케이스인 경우 true, 종료 조건(n=0, m=0)인 경우 false
     */
    private static boolean init() throws IOException {
        // 노드 수와 간선 수 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 트리 개수 초기화
        answer = 0;

        // 종료 조건: n=0, m=0인 경우
        if (n == 0 && m == 0) return false;

        // 그래프 초기화 (인접 리스트)
        graph = new ArrayList<>();

        // 방문 배열 초기화
        visited = new boolean[n + 1];

        // 그래프의 노드 리스트 초기화 (0번 인덱스는 사용하지 않음)
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            // 양방향 간선으로 그래프에 추가
            graph.get(node1).add(node2);
            graph.get(node2).add(node1);
        }

        return true;
    }

    /**
     * DFS를 수행하며 사이클 여부를 확인하는 메소드
     * @param current 현재 노드
     * @param parent 현재 노드의 부모 노드
     */
    private static void DFS(int current, int parent) {
        // 현재 노드와 연결된 모든 노드 탐색
        for (int node : graph.get(current)) {
            // 이미 방문한 노드이면서 부모 노드가 아닌 경우 => 사이클 발견
            if (visited[node] && node != parent) {
                isCycle = true;
                return;
            }
            // 아직 방문하지 않은 노드인 경우
            else if (!visited[node]) {
                // 노드 방문 처리
                visited[node] = true;

                // 다음 노드로 DFS 재귀 호출 (현재 노드를 부모로 설정)
                DFS(node, current);
            }
        }
    }
}