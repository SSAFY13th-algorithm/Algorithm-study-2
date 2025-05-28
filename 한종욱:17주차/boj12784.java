import java.io.*;
import java.util.*;

/**
 * 백준 12784번: 인하니카 공화국
 * 문제 난이도: Gold 3
 * 문제 유형: 트리 DP, DFS
 *
 * 문제 요약:
 * - N개의 섬으로 이루어진 인하니카 공화국에서 1번 섬만 남기고 모든 섬을 고립시키려 함
 * - 각 다리(간선)마다 폭파시키는 비용이 다름
 * - 모든 섬을 고립시키는 최소 비용을 구하는 문제
 *
 * 접근 방식:
 * - 트리 구조의 특성을 활용한 DFS와 동적 계획법 사용
 * - 각 노드에서 "자식 서브트리를 고립시키는 비용"과 "직접 간선을 끊는 비용" 중 최소값 선택
 *
 * 시간복잡도: O(N + M)
 * - N: 섬의 개수
 * - M: 다리의 개수
 * - 각 노드와 간선을 정확히 한 번씩만 방문하므로 선형 시간 복잡도
 */
public class boj12784 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static List<int[]>[] edges;  // 각 노드의 인접 리스트 (간선 정보)
    private static int n, m;             // n: 노드 수, m: 간선 수

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());  // 테스트 케이스 개수

        // 각 테스트 케이스 처리
        while (t-- > 0) {
            int answer = 0;
            init();  // 입력 및 초기화

            // n이 1이 아닌 경우만 DFS 수행 (섬이 하나일 경우 이미 고립됨)
            if (n != 1) answer = DFS(-1, 1);  // 루트(1번 섬)에서 DFS 시작

            sb.append(answer).append("\n");  // 결과 저장
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 그래프를 초기화하는 함수
     * 시간복잡도: O(N + M)
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 섬의 개수
        m = Integer.parseInt(st.nextToken());  // 다리의 개수

        // 섬이 하나일 경우 초기화 필요 없음
        if (n == 1) return;

        // 그래프(인접 리스트) 초기화
        edges = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            edges[i] = new ArrayList<>();
        }

        // 다리(간선) 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());  // 시작 섬
            int v = Integer.parseInt(st.nextToken());  // 도착 섬
            int w = Integer.parseInt(st.nextToken());  // 다리 폭파 비용

            // 양방향 간선 추가: [연결된 노드, 폭파 비용]
            edges[u].add(new int[] {v, w});
            edges[v].add(new int[] {u, w});
        }
    }

    /**
     * DFS를 통해 최소 폭파 비용을 계산하는 함수
     * @param prev 이전 노드 (부모)
     * @param current 현재 노드
     * @return 현재 노드의 서브트리를 고립시키는 최소 비용
     * 시간복잡도: O(N + M)
     */
    private static int DFS(int prev, int current) {
        boolean isLeaf = true;  // 현재 노드가 리프 노드인지 여부
        int cost = 0;           // 현재 노드의 서브트리 고립 비용

        // 현재 노드의 모든 인접 노드 탐색
        for (int[] next : edges[current]) {
            // 이전 노드(부모)가 아닌 경우만 처리 (자식 노드만 처리)
            if (next[0] != prev) {
                isLeaf = false;  // 자식이 있으므로 리프 노드가 아님

                // 자식 노드의 서브트리 고립 비용 계산 (재귀 호출)
                int childCost = DFS(current, next[0]);

                // 핵심 로직: 자식 서브트리 비용과 직접 간선 비용 중 최소값 선택
                // - childCost: 자식 서브트리를 모두 고립시키는 비용
                // - next[1]: 현재 노드와 자식 노드 사이의 다리를 직접 폭파하는 비용
                cost += Math.min(childCost, next[1]);
            }
        }

        // 리프 노드인 경우 (자식이 없고 부모가 있는 경우)
        if (isLeaf && prev != -1) {
            // 부모와의 다리 비용 반환 (리프 노드는 부모와의 연결만 끊으면 됨)
            return edges[current].get(0)[1];
        }

        // 내부 노드의 경우, 모든 자식에 대한 최소 비용의 합 반환
        return cost;
    }
}