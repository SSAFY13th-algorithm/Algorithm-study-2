import java.io.*;
import java.util.*;

public class Main {
    private static int[][] graph;
    private static int n;
    private static int[] team;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        graph = new int[n+1][n+1];
        for(int i=1; i<=n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            for(int j=1; j<=count; j++) {
                graph[i][Integer.parseInt(st.nextToken())] = 1;
            }
        }

        team = new int[n+1];
        team[1] = 1;
        dfs(1, 1);

        int count = 0;
        StringBuilder team1 = new StringBuilder();
        StringBuilder team2 = new StringBuilder();
        for(int i=1; i<=n; i++) {
            if(team[i] == 1) {
                count++;
                team1.append(i+" ");
                continue;
            }
            team2.append(i+" ");
        }

        System.out.println(count+"\n"+team1.toString().trim()+"\n"
                +(n-count)+"\n"+team2.toString().trim());
    }

    private static void dfs(int index, int teamColor) {
        if(index > n) {
            return;
        }
        for(int i=1; i<=n; i++) {
            if(graph[index][i] == 0) continue;
            if(team[i] == teamColor) break;

            team[i] = -teamColor;
        }
        if(index+1 > n) return;
        if(team[index+1] == 0) team[index+1] = teamColor;
        dfs(index+1, team[index+1]);
    }

}
