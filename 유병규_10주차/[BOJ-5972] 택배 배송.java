import java.io.*;
import java.util.*;

public class Main {
	static class Node{
		int index;
		int value;
		
		public Node(int index, int value) {
			this.index = index;
			this.value= value;
		}
	}
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        List<List<Node>> graph = new ArrayList<>();
        for(int i=0; i<n; i++) {
        	graph.add(new ArrayList<Node>());
        }
        
        for(int i=0; i<m; i++) {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken())-1;
        	int b = Integer.parseInt(st.nextToken())-1;
        	int k = Integer.parseInt(st.nextToken());
        	
        	graph.get(a).add(new Node(b, k));
        	graph.get(b).add(new Node(a, k));
        }
        
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        boolean[] visited = new boolean[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> dist[o1]-dist[o2]);
        dist[0] = 0;
        pq.offer(0);
        
        while(!pq.isEmpty()) {
        	int current = pq.poll();
        	
        	if(visited[current]) continue;
        	visited[current] = true;
        	
        	for(Node node : graph.get(current)) {
        		if(visited[node.index]) continue;
        		if(dist[node.index] <= dist[current]+node.value) continue;
        		dist[node.index] = dist[current]+node.value;
        		pq.offer(node.index);
        	}
        	
        }
        
        System.out.println(dist[n-1]);
    }
}
