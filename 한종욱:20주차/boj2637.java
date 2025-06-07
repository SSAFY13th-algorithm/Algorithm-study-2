import java.io.*;
import java.util.*;

public class boj2637 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 각 부품이 만들어지기 위해 필요한 하위 부품들의 정보를 저장
    private static List<Component>[] needs;

    // 각 부품의 진입차수 (해당 부품을 만들기 위해 필요한 상위 부품의 개수)
    private static int[] indegree;

    // arr[i][j]: i번 부품을 만들기 위해 필요한 j번 부품의 개수
    private static int[][] arr;

    private static int n; // 전체 부품의 개수 (완제품 번호가 n)
    private static int m; // 부품 관계의 개수

    public static void main(String[] args) throws IOException {
        init();
        topologySort(); // 위상 정렬을 통해 각 부품별 필요한 기본 부품 개수 계산

        // 완제품(n번 부품)을 만들기 위해 필요한 기본 부품들만 출력
        for (int i = 1; i <= n; i++) {
            if (arr[n][i] > 0) {
                bw.write(i + " " + arr[n][i] + "\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터 초기화
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine()); // 전체 부품 개수
        m = Integer.parseInt(br.readLine()); // 부품 관계 개수

        // 각 부품이 필요로 하는 하위 부품 리스트 초기화
        needs = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            needs[i] = new ArrayList<>();
        }

        indegree = new int[n + 1]; // 진입차수 배열
        arr = new int[n + 1][n + 1]; // 부품별 필요한 각 부품의 개수

        // 부품 관계 정보 입력
        while (m-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()); // 만들어지는 부품 번호
            int y = Integer.parseInt(st.nextToken()); // 필요한 부품 번호
            int k = Integer.parseInt(st.nextToken()); // 필요한 개수

            // x번 부품을 만들기 위해 y번 부품이 k개 필요함
            needs[x].add(new Component(y, k));
            indegree[x]++; // x번 부품의 진입차수 증가
        }
    }

    // 위상 정렬을 통한 부품별 필요한 기본 부품 개수 계산
    private static void topologySort() {
        Queue<Integer> q = new ArrayDeque<>();

        // 진입차수가 0인 부품들을 큐에 추가 (기본 부품들)
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                q.add(i);
                arr[i][i] = 1; // 기본 부품은 자기 자신이 1개 필요
            }
        }

        // 위상 정렬 수행
        while (!q.isEmpty()) {
            int current = q.poll(); // 현재 처리할 부품

            // 현재 부품을 필요로 하는 모든 상위 부품들을 확인
            for (int i = 1; i <= n; i++) {
                for (Component comp : needs[i]) {
                    // i번 부품이 current 부품을 필요로 하는 경우
                    if (comp.part == current) {
                        // i번 부품을 만들기 위해 필요한 각 기본 부품의 개수 업데이트
                        for (int j = 1; j <= n; j++) {
                            // current 부품을 comp.count개 만큼 필요하므로
                            // current를 만들기 위해 필요한 j번 부품도 comp.count배 만큼 필요
                            arr[i][j] += arr[current][j] * comp.count;
                        }

                        // i번 부품의 진입차수 감소
                        indegree[i]--;

                        // 진입차수가 0이 되면 큐에 추가 (모든 하위 부품이 준비됨)
                        if (indegree[i] == 0) {
                            q.add(i);
                        }
                        break; // 해당 부품을 찾았으므로 break
                    }
                }
            }
        }
    }

    // 부품과 필요한 개수를 저장하는 클래스
    static class Component {
        int part;  // 부품 번호
        int count; // 필요한 개수

        public Component(int part, int count) {
            this.part = part;
            this.count = count;
        }
    }
}