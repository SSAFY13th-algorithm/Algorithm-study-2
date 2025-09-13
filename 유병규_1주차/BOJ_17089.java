import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static int[][] graph;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        graph = new int[n+1][n+1];

        for(int i=0; i<m; i++) {
            int[] edge = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            graph[edge[0]][edge[1]] = 1;
            graph[edge[0]][0]++;
            graph[edge[1]][edge[0]] = 1;
            graph[edge[1]][0]++;
        }
        int min = Integer.MAX_VALUE;
        for(int i=1; i<=n; i++) {
            for(int j=i+1; j<=n; j++) {
                if(graph[i][j] == 0) continue;
                for(int k=j+1; k<=n; k++) {
                    if(graph[j][k] == 1 && graph[k][i] == 1) {
                        int total = graph[i][0]+graph[j][0]+graph[k][0] - 6;
                        min = Math.min(min, total);
                    }
                }
            }
        }

        if(min == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(min);
    }

}