import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int[][] map;
    private static boolean[][] visited;
    private static int count=0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        map = new int[m][n];

        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new boolean[m][n];

        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(map[i][j] == 0 || visited[i][j]) continue;
                count++;
                dfs(m, n, i, j);
            }
        }

        System.out.println(count);
    }

    private static void dfs(int m, int n, int i, int j) {
        if(i<0 || i>=m || j<0 || j>=n) {
            return;
        }

        if(visited[i][j]) return;
        if(map[i][j] == 0) return;
        visited[i][j] = true;
        int[][] d = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
        for(int x=0; x<d.length; x++) {
            int nx = i+d[x][0];
            int ny = j+d[x][1];
            dfs(m, n, nx, ny);
        }
    }

}