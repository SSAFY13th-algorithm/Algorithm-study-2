import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int p = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        
        List<Edge>[] graph = new ArrayList[p];
        for(int i=0; i<p; i++) {
        	graph[i] = new ArrayList<>();
        }
        
        for(int i=0; i<w; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	
        	graph[a].add(new Edge(b, c));
        	graph[b].add(new Edge(a, c));
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> -Integer.compare(o1[1], o2[1]));
        boolean[] visited = new boolean[p];
        pq.offer(new int[] {s, Integer.MAX_VALUE});
        
        while(!pq.isEmpty()) {
        	int[] info = pq.poll();
        	int current = info[0];
        	
        	if(current == e) {
        		System.out.println(info[1]);
        		return;
        	}
        	
        	if(visited[current]) continue;
        	visited[current] = true;
        	
        	for(Edge edge : graph[current]) {
        		if(visited[edge.to]) continue;
        		pq.offer(new int[] {edge.to, Math.min(info[1], edge.weight)});
        	}
        }
        
        System.out.println(0);
    }
    
    public static class Edge{
    	int to;
    	int weight;
    	
    	public Edge(int to, int weight) {
    		this.to = to;
    		this.weight = weight;
    	}
    }
}
