import java.io.*;
import java.util.*;

public class Main {
	private static int[][] map;
	private static int k;
	private static int[] numbers;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        numbers = new int[n];
        for(int i=0; i<n; i++) {
        	numbers[i] = Integer.parseInt(br.readLine(), 2);
        }
        
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken())-1;
        int end = Integer.parseInt(st.nextToken())-1;
        
        map = new int[n][n];
        
        Queue<Node> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        q.offer(new Node(start, (start+1)+""));
        visited[start] = true;
        while(!q.isEmpty()) {
        	Node node = q.poll();
        	int current = node.num;
        	
        	if(current == end) {
        		System.out.println(node.path);
        		return;
        	}
        	
        	for(int i=0; i<n; i++) {
        		if(visited[i]) continue;
        		if(map[current][i] > 1) continue;
        		if(dist(current, i) != 1) continue;
        		q.offer(new Node(i, node.path+" "+(i+1)));
        		visited[i] = true;
        	}
        }
        
        System.out.println(-1);
    }
    
    private static int dist(int idx1, int idx2) {
    	int a = numbers[idx1];
    	int b = numbers[idx2];
			int dist = 0;
			int number = a ^ b;
		
			for(int i=0; i<k; i++) {
				if((number & (1 << i)) != 0) dist++;
				
			}
			map[idx1][idx2] = dist;
			return dist;
		}
	
		private static class Node{
    	int num;
    	String path;
    	
    	public Node(int num, String path) {
    		this.num = num;
    		this.path = path;
    	}
    }
}
