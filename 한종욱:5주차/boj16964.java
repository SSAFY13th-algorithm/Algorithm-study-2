import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class boj16964 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 트리를 표현할 인접 리스트
    private static List<List<Integer>> graph = new ArrayList<>();
    // 주어진 순서를 저장할 배열
    private static int[] givenOrder;
    // DFS 방문 체크 배열
    private static boolean[] visited;
    // 노드의 개수와 현재 확인 중인 순서의 인덱스
    private static int n, index;

    public static void main(String[] args) throws IOException{
        init();  // 초기화
        if (dfs(1)) {  // 루트 노드(1)부터 DFS 시작
            bw.write(1 + "\n");  // 가능한 순서면 1 출력
        } else {
            bw.write(0 + "\n");  // 불가능한 순서면 0 출력
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException{
        // 노드의 개수 입력
        n = Integer.parseInt(br.readLine());
        index = 1;  // DFS 순서 체크를 위한 인덱스 초기화

        // 그래프 초기화 (0번 인덱스는 사용하지 않음)
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 배열들 초기화
        givenOrder = new int[n + 1];
        visited = new boolean[n + 1];

        // 트리의 간선 정보 입력 받기
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());
            // 양방향 간선 추가
            graph.get(node1).add(node2);
            graph.get(node2).add(node1);
        }

        // 주어진 순서 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            givenOrder[i] = Integer.parseInt(st.nextToken());
        }

        // order 배열 생성 (givenOrder의 역배열)
        int[] order = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            order[givenOrder[i]] = i;  // 각 노드가 몇 번째 순서인지 저장
        }

        // 각 노드의 인접 리스트를 주어진 순서(givenOrder)에 맞게 정렬
        for (int i = 1; i <= n; i++) {
            Collections.sort(graph.get(i), (a, b) -> order[a] - order[b]);
        }
    }

    private static boolean dfs(int node) {
        // 현재 방문한 노드가 주어진 순서와 다르면 false
        if (givenOrder[index] != node) {
            return false;
        }

        index++;  // 다음 순서 확인을 위해 인덱스 증가
        visited[node] = true;  // 현재 노드 방문 처리

        // 인접한 노드들을 방문
        for (int next : graph.get(node)) {
            if (!visited[next]) {  // 아직 방문하지 않은 노드만 방문
                if (!dfs(next)) return false;  // 하나라도 순서가 맞지 않으면 false
            }
        }
        return true;  // 모든 순서가 맞으면 true
    }
}