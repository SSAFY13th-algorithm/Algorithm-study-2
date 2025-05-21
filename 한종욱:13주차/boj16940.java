import java.io.*;
import java.util.*;

public class boj16940 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 그래프를 표현하는 인접 리스트
    private static List<Integer>[] graph;

    // 검증할 BFS 순서
    private static int[] answer;

    // 방문 여부를 체크하는 배열
    private static boolean[] visited;

    // 정점의 개수
    private static int n;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // BFS 순서 검증 (맞으면 1, 틀리면 0 출력)
        int k = BFS(1) ? 1 : 0;
        bw.write(String.valueOf(k));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력 데이터를 초기화하는 함수
     */
    private static void init() throws IOException {
        // 정점의 개수 입력
        n = Integer.parseInt(br.readLine());

        // 배열 및 그래프 초기화
        answer = new int[n];           // 검증할 BFS 순서
        visited = new boolean[n + 1];  // 정점 방문 여부 (1-indexed)
        graph = new List[n + 1];       // 그래프 인접 리스트 (1-indexed)

        // 각 정점의 인접 리스트 생성
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 간선 정보 입력 (n-1개)
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 양방향 그래프이므로 양쪽에 추가
            graph[u].add(v);
            graph[v].add(u);
        }

        // 검증할 BFS 순서 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            answer[i] = Integer.parseInt(st.nextToken());
        }
    }

    /**
     * BFS 순서 검증 함수
     * @param start 시작 정점
     * @return 주어진 순서가 유효한 BFS 순서이면 true, 아니면 false
     */
    private static boolean BFS(int start) {
        // BFS를 위한 큐
        Queue<Integer> q = new ArrayDeque<>();

        // 현재 레벨에서 방문 가능한 정점들을 저장하는 집합
        Set<Integer> set = new HashSet<>();

        // answer 배열의 인덱스 (첫 정점은 무조건 1이므로 다음 인덱스인 1부터 시작)
        int index = 1;

        // 시작 정점 방문 처리 및 큐에 추가
        visited[start] = true;
        q.add(start);

        // BFS 실행
        while (!q.isEmpty() && index < n) {
            // 현재 정점 꺼내기
            int current = q.poll();

            // 현재 정점의 모든 인접 정점 확인
            for (int next : graph[current]) {
                // 이미 방문한 정점은 건너뛰기
                if (visited[next]) continue;

                // 방문 처리 (큐에 바로 넣지 않고 set에 추가)
                visited[next] = true;
                set.add(next);
            }

            // 현재 레벨에서 방문 가능한 정점들 중에서 주어진 순서대로 처리
            while (!set.isEmpty() && index < n) {
                // 다음으로 방문해야 할 정점 (주어진 순서에 따라)
                int next = answer[index];

                // 다음 정점이 현재 레벨에서 방문 가능한 정점이 아니라면 순서가 잘못됨
                if (!set.contains(next)) {
                    return false;
                }

                // 방문 처리 및 큐에 추가
                visited[next] = true;
                set.remove(next);
                q.add(next);
                index++;
            }
        }

        // 모든 과정이 정상적으로 완료되면 유효한 BFS 순서
        return true;
    }
}