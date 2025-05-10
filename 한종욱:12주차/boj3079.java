import java.io.*;
import java.util.StringTokenizer;

public class boj3079 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();
    private static final int INF = (int)1e7;
    private static int[][] dist;
    private static int[] answer;
    private static int n, m;

    public static void main(String[] args) throws IOException {
        init();
        floydWarshall();

        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                int time = calTime(i, j);
                if (answer[2] > time) {
                    answer[0] = i;
                    answer[1] = j;
                    answer[2] = time;
                }
            }
        }

        sb.append(answer[0]).append(" ").append(answer[1]).append(" ").append(answer[2] * 2);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new int[n + 1][n + 1];
        answer = new int[3];
        answer[2] = INF;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dist[i][j] = INF;
            }
            dist[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            dist[x][y] = 1;
            dist[y][x] = 1;
        }

    }

    private static void floydWarshall() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][k] > 0 && dist[k][j] > 0) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
    }

    private static int calTime(int n1, int n2) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (i == n1 || i == n2) continue;

            sum += Math.min(dist[n1][i], dist[n2][i]);
        }

        return sum;
    }
}