import java.io.*;
import java.util.*;

public class Main {
	static class Edge implements Comparable{
		int a, b;
		double value;
		
		public Edge(int a, int b, double value) {
			this.a = a;
			this.b = b;
			this.value = value;
		}

		@Override
		public int compareTo(Object o) {
			Edge other = (Edge) o;
			return Double.compare(this.value, other.value);
		}
	}
	
	private static int[] parent;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        
        if(n == 1) {
        	System.out.println(0);
        	return;
        }
        
        double[][] points = new double[n][2];
        for(int i=0; i<n; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	double a = Double.parseDouble(st.nextToken());
        	double b = Double.parseDouble(st.nextToken());
        	points[i] = new double[] {a, b};
        }
        
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for(int i=0; i<n; i++) {
        	for(int j=i+1; j<n; j++) {
        		Edge edge = new Edge(i, j, dist(points[i], points[j]));
        		pq.offer(edge);
        	}
        }
        
        parent = new int[n];
        for(int i=0; i<n; i++) {
        	parent[i] = i;
        }
        
        int edgeCount = 0;
        double result = 0;
        while(!pq.isEmpty() && edgeCount < n-1) {
        	Edge edge = pq.poll();
        	
        	if(union(edge.a, edge.b)) {
        		result += edge.value;
        		edgeCount++;
        	}
        }
        
        System.out.println(Math.round(result*100)/100.0);
    }
    
    private static double dist(double[] a, double[] b) {
    	double dx = Math.abs(a[0]-b[0]);
    	double dy = Math.abs(a[1]-b[1]);
    	return Math.sqrt(dx*dx + dy*dy);
    }
    
    private static boolean union(int a, int b) {
    	a = findRoot(a);
    	b = findRoot(b);
    	
    	if(a == b) return false;
    	
    	parent[b] = a;
    	return true;
    }
    
    private static int findRoot(int x) {
    	if(parent[x] == x) return x;
    	return parent[x] = findRoot(parent[x]);
    }
}
