import java.io.*;
import java.util.StringTokenizer;

public class boj7511 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static int[] uf;  // Union-Find를 위한 배열
    private static int n, k, m;  // n: 유저 수, k: 관계 수, m: 쿼리 수

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());  // 테스트 케이스 수

        // 각 테스트 케이스 처리
        for (int tc = 1; tc <= T; tc++) {
            sb.append("Scenario ").append(tc).append(":\n");
            init();  // 초기화 및 쿼리 처리

            // 마지막 테스트 케이스가 아니라면 빈 줄 추가
            if (tc < T) {
                sb.append("\n");
            }
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기화 및 쿼리 처리 메소드
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 유저 수
        k = Integer.parseInt(br.readLine());  // 친구 관계 수

        // Union-Find 배열 초기화
        uf = new int[n];
        for (int i = 0; i < n; i++) {
            uf[i] = i;  // 각 노드는 처음에 자기 자신을 가리킴
        }

        // 친구 관계 입력 및 처리
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            union(node1, node2);  // 두 유저를 같은 집합으로 합침
        }

        // 쿼리 처리
        m = Integer.parseInt(br.readLine());  // 쿼리 수
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            // 두 유저가 같은 집합에 속하는지 확인
            if (find(node1) == find(node2)) {
                sb.append(1);  // 연결되어 있음
            } else {
                sb.append(0);  // 연결되어 있지 않음
            }
            sb.append("\n");
        }
    }

    // Union 연산: 두 집합을 합치는 메소드
    private static void union(int x, int y) {
        int X = find(x);  // x의 루트 찾기
        int Y = find(y);  // y의 루트 찾기

        uf[X] = Y;  // x의 루트를 y의 루트로 변경
    }

    // Find 연산: 요소가 속한 집합의 루트를 찾는 메소드 (경로 압축 포함)
    private static int find(int x) {
        if (uf[x] == x) return x;  // 자기 자신이 루트면 반환
        return uf[x] = find(uf[x]);  // 경로 압축: 재귀적으로 루트를 찾고 바로 루트를 가리키도록 업데이트
    }
}