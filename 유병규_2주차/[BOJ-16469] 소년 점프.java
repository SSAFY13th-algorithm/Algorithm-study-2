import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int r, c;
    private static int[][] map;
    private static int[] villain1, villain2, villain3;
    private static int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    private static int[][] dist1, dist2, dist3;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new int[r + 1][c + 1];
        dist1 = new int[r + 1][c + 1];
        dist2 = new int[r + 1][c + 1];
        dist3 = new int[r + 1][c + 1];
        for (int i = 1; i <= r; i++) {
            String line = br.readLine();
            for (int j = 1; j <= c; j++) {
                map[i][j] = Integer.parseInt(line.charAt(j-1) + "");
                dist1[i][j] = dist2[i][j] = dist3[i][j] = -1;
            }
        }

        villain1 = createPoint(br, st);
        villain2 = createPoint(br, st);
        villain3 = createPoint(br, st);

        bfs(villain1, dist1);
        bfs(villain2, dist2);
        bfs(villain3, dist3);

        int min = Integer.MAX_VALUE;
        int count = 0;
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                if (map[i][j] == 1) continue;
                if(dist1[i][j] == -1 || dist2[i][j] == -1 || dist3[i][j] == -1) continue;

                int value = Math.max(Math.max(dist1[i][j], dist2[i][j]), dist3[i][j]);

                if(min == value) count++;
                else if(min > value) {
                    min = value;
                    count = 1;
                }
            }
        }

        if(min == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(min+"\n"+count);
    }

    private static int[] createPoint(BufferedReader br, StringTokenizer st) throws IOException {
        st = new StringTokenizer(br.readLine());
        return new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
    }

    private static void bfs(int[] villain, int[][] dist) {
        Queue<int[]> queue = new LinkedList<>();

        dist[villain[0]][villain[1]] = 0;
        queue.offer(new int[]{villain[0], villain[1]});

        while(!queue.isEmpty()) {
            int[] point = queue.poll();

            for(int i=0; i<d.length; i++) {
                int nx = point[0] + d[i][0];
                int ny = point[1] + d[i][1];

                if(nx<1 || nx>r || ny<1 || ny>c || map[nx][ny] == 1 || dist[nx][ny] != -1) continue;

                dist[nx][ny] = dist[point[0]][point[1]] + 1;
                queue.offer(new int[] {nx, ny});
            }
        }
    }
}
