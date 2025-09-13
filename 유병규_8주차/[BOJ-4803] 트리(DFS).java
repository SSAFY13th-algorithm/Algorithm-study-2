import java.io.*;
import java.util.*;

public class Main {
	private static String NO_TREE = "No trees.";
	private static String ONE_TREE = "There is one tree.";
	private static String N_TREE_FORMAT = "A forest of %d trees.";
	
	private static int n, m, count;
	private static boolean[][] graph;
	private static boolean[] visited;
    private static boolean isTree;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCase = 1;
        while(true) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	n = Integer.parseInt(st.nextToken());
        	m = Integer.parseInt(st.nextToken());
        	
        	if(n==0 && m==0) {
        		System.out.println(sb.toString().trim());
        		return;
        	}
        	
        	graph = new boolean[n+1][n+1];
        	for(int i=0; i<m; i++) {
        		st = new StringTokenizer(br.readLine());
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		
        		graph[a][b] = true;
        		graph[b][a] = true;
        	}
        	
        	visited = new boolean[n+1];
        	count = 0;
        	for(int i=1; i<=n; i++) {
        		if(visited[i]) continue;
        		isTree = true;
        		dfs(i, -1);
        		if(isTree) count++;
        	}
        	
        	sb.append(String.format("Case %d: ", testCase));
        	if(count == 0) sb.append(NO_TREE).append("\n");
        	else if(count == 1) sb.append(ONE_TREE).append("\n");
        	else sb.append(String.format(N_TREE_FORMAT, count)).append("\n");
        	
        	testCase++;
        }
    }

	private static void dfs(int node, int parent) {
		visited[node] = true;
		
		for(int i=1; i<=n; i++) {
			if(!graph[node][i] || parent == i) continue;
			if(visited[i]) {
				isTree = false;
				continue;
			}
			dfs(i, node);
		}
	}
}
