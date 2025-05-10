import java.io.*;
import java.util.StringTokenizer;

public class boj21278 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 무한대 값을 표현하기 위한 상수
    private static final int INF = (int)1e7;

    // 각 지점 간의 최단 거리를 저장하는 배열
    private static int[][] dist;

    // 정답을 저장하는 배열 [도시1, 도시2, 최소시간]
    private static int[] answer;

    // n: 도시의 수, m: 도로의 수
    private static int n, m;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // 플로이드-워셜 알고리즘으로 모든 쌍 최단 경로 찾기
        floydWarshall();

        // 모든 두 도시 쌍에 대해 최소 시간 계산
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // i번 도시와 j번 도시에 경찰서를 세웠을 때 총 소요 시간
                int time = calTime(i, j);

                // 기존 최소 시간보다 작으면 정답 갱신
                if (answer[2] > time) {
                    answer[0] = i;     // 첫 번째 경찰서 위치
                    answer[1] = j;     // 두 번째 경찰서 위치
                    answer[2] = time;  // 최소 시간
                }
            }
        }

        // 결과 출력 (왕복 시간이므로 *2)
        sb.append(answer[0]).append(" ").append(answer[1]).append(" ").append(answer[2] * 2);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력 데이터를 초기화하는 함수
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 도시의 수
        m = Integer.parseInt(st.nextToken()); // 도로의 수

        // 거리 배열 초기화
        dist = new int[n + 1][n + 1];

        // 정답 배열 초기화
        answer = new int[3];
        answer[2] = INF;  // 최소 시간을 무한대로 초기화

        // 거리 배열의 모든 값을 무한대로 초기화
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dist[i][j] = INF;
            }
            dist[i][i] = 0;  // 자기 자신까지의 거리는 0
        }

        // 도로 정보 입력 받기
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            dist[x][y] = 1;  // x에서 y로 가는 도로 (가중치 1)
            dist[y][x] = 1;  // y에서 x로 가는 도로 (양방향)
        }
    }

    /**
     * 플로이드-워셜 알고리즘으로 모든 쌍 최단 경로를 계산하는 함수
     */
    private static void floydWarshall() {
        // k: 중간 경유 도시
        for (int k = 1; k <= n; k++) {
            // i: 출발 도시
            for (int i = 1; i <= n; i++) {
                // j: 도착 도시
                for (int j = 1; j <= n; j++) {
                    // i에서 k를 거쳐 j로 가는 경로가 더 짧으면 갱신
                    if (dist[i][k] > 0 && dist[k][j] > 0) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
    }

    /**
     * 두 도시에 경찰서를 세웠을 때 총 소요 시간을 계산하는 함수
     * @param n1 첫 번째 경찰서 위치
     * @param n2 두 번째 경찰서 위치
     * @return 모든 도시에서 가장 가까운 경찰서까지의 거리 합
     */
    private static int calTime(int n1, int n2) {
        int sum = 0;

        // 모든 도시에 대해
        for (int i = 1; i <= n; i++) {
            // 경찰서가 있는 도시는 제외
            if (i == n1 || i == n2) continue;

            // i번 도시에서 가장 가까운 경찰서까지의 거리를 더함
            sum += Math.min(dist[n1][i], dist[n2][i]);
        }

        return sum;
    }
}