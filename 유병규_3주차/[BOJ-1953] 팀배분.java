import java.io.*;
import java.util.*;

public class Main {
	private static int n;
	private static int[][] graph;
	private static int[] teams;
	private static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		n = Integer.parseInt(br.readLine());
		
		graph = new int[n+1][n+1];
		teams = new int[n+1];
		for(int i=1; i<=n; i++) {
			st = new StringTokenizer(br.readLine());
			int repeat = Integer.parseInt(st.nextToken());
			for(int j=0; j<repeat; j++) {
				graph[i][Integer.parseInt(st.nextToken())] = 1;
			}
		}
		
		for(int i=1; i<=n; i++) {
			if(teams[i] == 0) {
				dfs(i, 1);
			}
		}
		
		printTeam(1);
		printTeam(-1);
		System.out.println(sb);
	}

	private static void printTeam(int team) {
		int count = 0;
		for(int i=1; i<=n; i++) {
			if(teams[i] != team) continue;
			count++;
		}
		sb.append(count).append("\n");
		for(int i=1; i<=n; i++) {
			if(teams[i] != team) continue;
			sb.append(i).append(" ");
		}
		sb.append("\n");
	}

	private static void dfs(int start, int teamColor) {
		if(teams[start] == 0) {
			teams[start] = teamColor;
			for(int i=1; i<=n; i++) {
				if(graph[start][i] == 0) continue;
				dfs(i, teamColor*-1);
			}
		}
	}
}
