import java.io.*;
import java.util.*;

public class Main {
	private static String NO_TREE = "No trees.";
	private static String ONE_TREE = "There is one tree.";
	private static String N_TREE_FORMAT = "A forest of %d trees.";
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCase = 1;
        while(true) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	int n = Integer.parseInt(st.nextToken());
        	int m = Integer.parseInt(st.nextToken());
        	
        	if(n==0 && m==0) {
        		System.out.println(sb.toString().trim());
        		return;
        	}
        	
        	boolean[][] graph = new boolean[n+1][n+1];
        	for(int i=0; i<m; i++) {
        		st = new StringTokenizer(br.readLine());
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		
        		graph[a][b] = true;
        		graph[b][a] = true;
        	}
        	
        	boolean[] visited = new boolean[n+1];
        	int count = 0;
        	
        	Queue<Integer> q = new LinkedList<>();
        	int[] parent = new int[n+1];
        	for(int node=1; node<=n; node++) {
        		if(visited[node]) continue;
        		boolean isTree = true;
        		visited[node] = true;
        		q.offer(node);
        		while(!q.isEmpty()) {
        			int current= q.poll();
        			
        			for(int i=1; i<=n; i++) {
        				if(!graph[current][i] || i==parent[current]) continue;
        				if(visited[i]) {
        					isTree = false;
        					continue;
        				}
        				visited[i] = true;
        				parent[i] = current;
        				q.offer(i);
        			}
        		}
        		if(isTree) count++;
        	}
        	
        	sb.append(String.format("Case %d: ", testCase));
        	if(count == 0) sb.append(NO_TREE).append("\n");
        	else if(count == 1) sb.append(ONE_TREE).append("\n");
        	else sb.append(String.format(N_TREE_FORMAT, count)).append("\n");
        	
        	testCase++;
        }
    }
}
