import java.io.*;
import java.util.*;

public class Main {
	private static int n;
	private static int[] parent;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
    	StringBuilder sb = new StringBuilder();
    	int repeat = Integer.parseInt(br.readLine());
    	for(int testCase=1; testCase<=repeat; testCase++) {
    		sb.append(String.format("Scenario %d:%n", testCase));
    		
    		n = Integer.parseInt(br.readLine());
    		int k = Integer.parseInt(br.readLine());
    		
    		parent = new int[n];
    		for(int i=0; i<n; i++) {
    			parent[i] = i;
    		}
    		
    		for(int i=0; i<k; i++) {
    			StringTokenizer st = new StringTokenizer(br.readLine());
    			int a = Integer.parseInt(st.nextToken());
    			int b = Integer.parseInt(st.nextToken());
    			
    			union(a,b);
    		}
    		
    		int m = Integer.parseInt(br.readLine());
    		for(int i=0; i<m; i++) {
    			StringTokenizer st = new StringTokenizer(br.readLine());
    			int a = Integer.parseInt(st.nextToken());
    			int b = Integer.parseInt(st.nextToken());
    			
    			int result = 0;
    			if(findRoot(a) == findRoot(b)) result = 1;
    			sb.append(result).append("\n");
    		}
    		sb.append("\n");
    	}
    	
    	System.out.println(sb.toString().trim());
    }

	private static void union(int a, int b) {
		a = findRoot(a);
		b = findRoot(b);
		
		if(a != b) {
			parent[b] = a;
		}
	}

	private static int findRoot(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findRoot(parent[x]);
	}
}
